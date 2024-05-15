package com.xebia.errors

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.raise.recover
import arrow.core.right
import arrow.fx.stm.TMap
import arrow.fx.stm.atomically
import com.xebia.MediaAlreadyAdded
import com.xebia.MediaId
import com.xebia.Playlist
import com.xebia.PlaylistAlreadyExists
import com.xebia.PlaylistError
import com.xebia.PlaylistId
import com.xebia.PlaylistNotFound
import com.xebia.PlaylistRepository
import com.xebia.historyAsPlaylist
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration
import kotlinx.coroutines.runBlocking
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.Size
import net.jqwik.api.constraints.UniqueElements
import kotlin.test.DefaultAsserter.assertTrue

class TypedErrorsTest {
  @Property(tries = 20)
  fun happyPathReturnEither(
    @ForAll @Size(min = 1, max = 100) @UniqueElements history: List<Int>
  ) = runBlocking {
    val repo = TestPlaylistRepo(history.map { MediaId(it) }, TMap.new(), TMap.new())
    val res: Any = repo.historyAsPlaylist("new-playlist", Duration.ZERO)
    assertTrue("Happy path should return Either", res is Either.Right<*>)
  }

  @Property(tries = 20)
  fun recoverFromPlaylistAlreadyExists(
    @ForAll @Size(min = 1, max = 100) @UniqueElements history: List<Int>
  ) = runBlocking {
    val repo = TestPlaylistRepo(history.map { MediaId(it) }, TMap.new(), TMap.new())
    repo.newPlaylist("new-playlist")
    val res: Any = repo.historyAsPlaylist("new-playlist", Duration.ZERO)
    assertTrue("Playlist already exists, but it was not recovered from. Found: $res", res is Either.Right<*>)
  }

  @Property(tries = 20)
  fun returnAllMediaThatAlreadyExisted(
    @ForAll @Size(min = 1, max = 100) @UniqueElements history: List<Int>
  ) = runBlocking {
    val repo = TestPlaylistRepo(history.map { MediaId(it) }, TMap.new(), TMap.new())
    val id = recover({ repo.newPlaylist("new-playlist").bind() }) { it.id }
    history.forEach { repo.addMedia(id, MediaId(it)) }
    val res: Any = repo.historyAsPlaylist("new-playlist", Duration.ZERO)
    val passed = if(res is Either.Left<*> && res.value is NonEmptyList<*>) {
      (res.value as NonEmptyList<*>).filterIsInstance<MediaAlreadyAdded>().size == history.size
    } else false
    assertTrue("Expected all mediaIds that already existed but found $res", passed)
  }

  private class TestPlaylistRepo(
    private val ids: List<MediaId>,
    private val map: TMap<String, PlaylistId>,
    private val content: TMap<PlaylistId, List<MediaId>>
  ) : PlaylistRepository {
    private val idCounter = AtomicInteger(0)

    override suspend fun newPlaylist(name: String): Either<PlaylistAlreadyExists, PlaylistId> =
      atomically {
        when (val id = map[name]) {
          null -> {
            val playlistId = PlaylistId(idCounter.getAndIncrement())
            map[name] = playlistId
            content[playlistId] = emptyList()
            playlistId.right()
          }
          else -> PlaylistAlreadyExists(id, name).left()
        }
      }

    override suspend fun addMedia(playlistId: PlaylistId, mediaId: MediaId): Either<PlaylistError, Unit> =
      atomically {
        when (val media = content[playlistId]) {
          null -> PlaylistNotFound(playlistId).left()
          else -> {
            when (mediaId) {
              in media -> MediaAlreadyAdded(playlistId, mediaId).left()
              else -> content.update(playlistId) { it + mediaId }.right()
            }
          }
        }
      }

    override suspend fun history(duration: Duration): Playlist {
      return Playlist(PlaylistId(-1), "history", ids)
    }
  }
}

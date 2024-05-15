package com.xebia

import arrow.core.*
import arrow.core.raise.either
import kotlin.time.Duration

sealed interface PlaylistError
data class PlaylistAlreadyExists(val id: PlaylistId, val name: String) : PlaylistError
data class MediaAlreadyAdded(val playlistId: PlaylistId, val media: MediaId) : PlaylistError
data class PlaylistNotFound(val id: PlaylistId) : PlaylistError

@JvmInline
value class MediaId(val id: Int)

@JvmInline
value class PlaylistId(val id: Int)
data class Playlist(val id: PlaylistId, val name: String, val media: List<MediaId>)

interface PlaylistRepository {
  suspend fun newPlaylist(name: String): Either<PlaylistAlreadyExists, PlaylistId>
  suspend fun addMedia(playlistId: PlaylistId, mediaId: MediaId): Either<PlaylistError, Unit>
  suspend fun history(duration: Duration): Playlist
}

suspend fun PlaylistRepository.historyAsPlaylist(name: String, duration: Duration): EitherNel<PlaylistError, Unit> =
  either {
    val playlistId = newPlaylist(name).getOrElse { it.id }
    val history = history(duration)

    history.media.mapOrAccumulate { media ->
      addMedia(playlistId, media).bind()
    }.bind()
  }

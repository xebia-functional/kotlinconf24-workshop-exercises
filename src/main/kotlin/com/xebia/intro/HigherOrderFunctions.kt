package com.xebia.com.xebia.intro

import java.time.LocalDate
import kotlin.random.Random

enum class Genre {
    Rock, Pop, Jazz
}

data class Artist(val name: String, val feePerSongPlayed: Double)

data class Song(val title: String, val artist: Artist, val genre: Genre, val releaseDate: LocalDate)

fun Song.belongToGenreAndWasReleasedAfter(date: LocalDate, selectedGenre: Genre): Boolean =
    releaseDate.isAfter(date) && genre == selectedGenre

fun calculateRoyalties(song: Song): Double {
    val playCount = Random.nextInt(100, 1000) // Simulate a call to an external service
    return playCount * song.artist.feePerSongPlayed
}

fun main() {
    val songsLibrary: List<Song> = emptyList()
    val date = LocalDate.of(2024, 1, 1)

    val royaltiesForPopSongsReleasedIn2024 = songsLibrary
        .filter { it.belongToGenreAndWasReleasedAfter(date, Genre.Pop) }
        .map(::calculateRoyalties)
        .sum() // We can also propose learners to find an alternative with fold
}
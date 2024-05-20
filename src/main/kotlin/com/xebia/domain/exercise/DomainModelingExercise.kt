package com.xebia.com.xebia.domain.exercise

import java.time.LocalDate
import java.time.Month
import java.time.Year
import kotlin.time.Duration

val passwordRegex: Regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex()

@JvmInline
value class Password private constructor(val value: String) {
    override fun toString(): String {
        return "<REDACTED>"
    }

    companion object {
        fun build(password: String): Password? {
            return if (!password.contains(passwordRegex)) null
            else Password(password)
        }
    }
}

sealed class User {
    abstract val id: Long
    abstract val username: String
    abstract val password: Password

    data class FreeUser(
        override val id: Long, override val username: String, override val password: Password, val skipLimit: Int
    ) : User()

    data class PremiumUser(
        override val id: Long,
        override val username: String,
        override val password: Password,
        val paymentInfo: PaymentInfo
    ) : User()
}

data class CreditCard(val cardNumber: CardNumber, val expirationDate: ExpirationDate) {
    @JvmInline
    value class CardNumber(val number: String) {
        override fun toString(): String {
            return "REDACTED"
        }
    }

    data class ExpirationDate(val month: Month, val year: Year)
}

sealed interface PaymentInfo {
    data class PayPal(val email: String) : PaymentInfo
    data class Credit(val creditCard: CreditCard)
}

data class Artist(val id: Long, val name: String)

enum class Genre {
    Rock, Pop, Jazz, Classical
}

data class Song(
    val id: Long,
    val title: String,
    val artist: Artist,
    val genre: Genre,
    val releaseDate: LocalDate,
    val duration: Duration
)

data class Playlist(
    val id: Long, val name: String, val user: User.PremiumUser, val songs: List<Song>
)

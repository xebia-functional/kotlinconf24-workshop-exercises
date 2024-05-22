package com.xebia.domain

import kotlin.time.Duration

/**
 * Primitives are the basis of our type system,
 * but using them too much causes primitive obsession.
 * Which means that simply by type,
 * we cannot understand the structure our data type.
 *
 * Refactor this code, with as little as additional code as possible,
 * **little code as possible**.
 */
data class Article(
    val id: Long,
    val title: String,
    val description: String,
    val averageReadingTime: Long
)

@JvmInline
value class Title(val value: String) {
    companion object {
        fun build(title: String): Title? {
            return if (title.isBlank() || title.length !in 10..50)
                null
            else
                Title(title)
        }
    }
}
data class ImprovedArticle(
    val id: Long,
    val title: Title,
    val description: String,
    val averageReadingTime: Duration
)
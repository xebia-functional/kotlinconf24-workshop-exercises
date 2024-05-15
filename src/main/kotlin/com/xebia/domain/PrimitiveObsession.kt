package com.xebia.com.xebia.domain

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

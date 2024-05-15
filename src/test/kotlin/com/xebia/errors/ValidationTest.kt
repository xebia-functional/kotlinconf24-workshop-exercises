package com.xebia.errors

import arrow.core.Either
import arrow.core.nonEmptyListOf
import com.xebia.DuplicateAuthor
import com.xebia.EmptyAuthorName
import com.xebia.EmptyTitle
import com.xebia.MediaInfo
import com.xebia.NoAuthors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValidationTest {
  @Test
  fun testOk() {
    val corrs = MediaInfo("Would you be happier?", listOf("The Corrs"))
    assertTrue(corrs is Either.Right<*>, "media info is correct")
  }

  @Test
  fun testNoAuthors() {
    val weird = MediaInfo("Hello!", emptyList())
    assertEquals(weird, Either.Left(nonEmptyListOf(NoAuthors)))
  }

  @Test
  fun testEmpty() {
    val weird = MediaInfo("", listOf("", ""))
    assertEquals(weird, Either.Left(nonEmptyListOf(EmptyTitle, EmptyAuthorName, EmptyAuthorName)))
    val weirder = MediaInfo("", emptyList())
    assertEquals(weirder, Either.Left(nonEmptyListOf(EmptyTitle, NoAuthors)))
  }

  @Test
  fun testDuplicates() {
    val weird = MediaInfo("Hello!", listOf("A", "B", "A"))
    assertEquals(weird, Either.Left(nonEmptyListOf(DuplicateAuthor("A"))))
    val weirder = MediaInfo("", listOf("A", "B", "A"))
    assertEquals(weirder, Either.Left(nonEmptyListOf(EmptyTitle, DuplicateAuthor("A"))))
  }
}
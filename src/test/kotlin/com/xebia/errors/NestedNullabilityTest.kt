package com.xebia.errors

import com.xebia.com.xebia.errors.exercise1.mapFromChosen
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NestedNullabilityTest {
  @Test
  fun testSolution() {
    val elements = listOf(3, null, 4)
    val result = elements.mapFromChosen({ true }) { "$it" }
    assertEquals(result.size, elements.size)
    assertEquals(elements.toList(), result.values.toList())
  }

  @Test
  fun testSolutionFilter() {
    val elements = listOf(3, null, 4)
    val result = elements.mapFromChosen({ it != null })  { "$it" }
    assertEquals(2, result.size)
    assertEquals(elements.filterNotNull(), result.values.toList())
  }
}
package com.xebia.errors

import com.xebia.mapFromChosen
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NestedNullabilityTest {
  @Test
  fun testSolution() {
    val elements = listOf(3, null, 4)
    val result = elements.mapFromChosen({ true }) { "$it" }
    assertEquals(result.size, elements.size)
    assertEquals(elements, result.values)
  }

  @Test
  fun testSolutionFilter() {
    val elements = listOf(3, null, 4)
    val result = elements.mapFromChosen({ it != null })  { "$it" }
    assertEquals(2, result.size)
    assertTrue(elements.filterNotNull() == result.values)
  }
}
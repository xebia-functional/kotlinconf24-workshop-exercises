package com.xebia.domain

import com.xebia.mapFromChosen
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BooleanObsessionTest {
    @Test
    fun testSolutionFilter() {
        val elements = listOf(3, null, 4)
        val result = elements.mapFromChosen({ it != null })  { it }
        assertEquals(2, result.size)
        assertTrue(elements.filterNotNull().containsAll(result.values))
    }
}
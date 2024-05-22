package com.xebia.domain

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NotBlank
import net.jqwik.api.constraints.NotEmpty
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SmartConstructorsTest {
  @Test
  fun testEmpty() {
    assertNull(Name(""))
  }

  @Property(tries = 20)
  fun testInsertion(@ForAll @NotEmpty @NotBlank name: String) {
    assertEquals(name, Name(name)?.name)
  }
}
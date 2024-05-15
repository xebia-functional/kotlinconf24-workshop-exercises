package com.xebia.com.xebia.domain

/**
 * Our Name class cannot be created with a null, or blank String.
 * So we need to implement a smart-constructor that disallows that!
 *
 * Think about what other constraints would be interesting to add,
 * and if multiple violations would be possible.
 */
@JvmInline
value class Name private constructor(val name: String) {
  companion object {
    // TODO implement smart-constructor
    operator fun invoke(name: String?): Name? =
      if (name.isNullOrBlank()) null else Name(name)
  }
}

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

/**
 * In this case, the selected price for the concert ticket should meet two requirements:
 * - The price should be within the range of $35 to $125.
 * - The price must be a multiple of 5.
 * So we need to implement a smart-constructor that disallows that!
 *
 * Think about what other constraints would be interesting to add,
 * and if multiple violations would be possible.
 */
@JvmInline
value class TicketPrice private constructor (val value: Double) {
  companion object {
    private const val MINIMUM_PRICE: Double = 35.0
    private const val MAXIMUM_PRICE: Double = 125.0

    operator fun invoke(price: Double): TicketPrice? {
      return if (price in MINIMUM_PRICE..MAXIMUM_PRICE) {
          if (price % 5 == 0.0) {
            TicketPrice(price)
          } else {
            null
          }
      } else {
        null
      }
    }
  }
}
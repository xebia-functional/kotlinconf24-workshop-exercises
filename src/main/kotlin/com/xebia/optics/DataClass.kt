package com.xebia.com.xebia.optics

import arrow.core.toOption
import arrow.optics.Lens
import arrow.optics.Optional

/**
 * Product types, or Data Classes, exist out of 1..n properties.
 * To allow us to work with MyDataClass in an elegant way,
 * we want to be able to work with Optics.
 *
 * MyDataClass has a non-null, and nullable property.
 * So we want to implement a [Lens], and [Optional] respectively.
 *
 * Steps:
 *  1. Implement `property: Lens`.
 *  2. Implement `nullable: Lens`.
 */
data class DataClass(
    val property: String,
    val nullable: String?
) {
    companion object {
        val property: Lens<DataClass, String> =
            // TODO implement Lens
            Lens(DataClass::property) { instance, property ->
                instance.copy(property = property)
            }

        val nullable: Optional<DataClass, String> =
            // TODO implement Optional
            Optional({ it.nullable.toOption() }) { instance, nullable ->
                instance.copy(nullable = nullable)
            }
    }
}

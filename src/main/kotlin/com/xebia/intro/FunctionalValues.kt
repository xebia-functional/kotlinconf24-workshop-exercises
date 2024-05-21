package com.xebia.com.xebia.intro

/**
 * In this exercise we'll practice how to convert functional values into functions and vice versa.
 *
 */

fun booleanConversionAsFunction(value: String): Boolean? = value.toBooleanStrictOrNull()

/**
 * Use the [booleanConversionAsFunction] function to define a functional value that converts [String] into [Boolean]
 */
val booleanConversionAsFunctionalValue: (String) -> Boolean? = TODO()

/**
 * Implement a functional value to convert [String] values into [Int]. Return null if the [String] is not a valid [Int]
 */
val intConversionAsFunctionalValue: (String) -> Int? = TODO()

/**
 * Use the [intConversionAsFunctionalValue] functional value to define a function that converts [String] into [Int]
 */
fun intConversionAsFunction(value: String): Int? = TODO()

/**
 * In this exercise, we'll practice how to pass functions and functional values as parameters to higher-order functions.
 *
 * To do so, we'll have to convert a list of [String] values into [Int] by using [intConversionAsFunctionalValue] and
 * [intConversionAsFunction]. We want to remove null values from the result list, so we'll use [mapNotNull] to ensure
 * the result only contains valid [Int] values.
 *
 * Consider the following alternatives to solve the exercise:
 * - Option A: Invoke the [intConversionAsFunction] function inside a lambda
 * - Option B: Invoke the [intConversionAsFunctionalValue] functional value inside a lambda
 * - Option C: Pass the function reference of [intConversionAsFunction] as a parameter to [mapNotNull]
 * - Option D: Pass [intConversionAsFunctionalValue] as a parameter to [mapNotNull]
 */
fun main() {
    val stringValues = listOf("1", "a", "5", "abc", "7")
    val convertedIntValuesOptionA = TODO()
    val convertedIntValuesOptionB = TODO()
    val convertedIntValuesOptionC = TODO()
    val convertedIntValuesOptionD = TODO()

    assert(convertedIntValuesOptionA == listOf(1, 5, 7))

    assert(convertedIntValuesOptionA == convertedIntValuesOptionB
            && convertedIntValuesOptionA == convertedIntValuesOptionC
            && convertedIntValuesOptionA == convertedIntValuesOptionD
    )
}

package com.xebia.com.xebia.errors.transforming

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.withError
import arrow.core.raise.either

/**
 * Exercise: Transforming errors
 *
 * Often we need to transform errors,
 * when we cross layer boundaries,
 * or when translating specific errors to use-case errors.
 *
 * In this exercise we have: [MyError], and [OtherError].
 * We also get a transformation from [MyError.toOtherError],
 * which we'll need to use to transform from [MyError] to [OtherError].
 *
 * 1. Implement [either]
 * 2. Implement [raise]
 *
 * Additional exercise:
 * [Either], and [Raise] allow achieving the same behavior,
 * and they work seamlessly together.
 *
 * Try to solve 1 using the solution used in 2,
 * and vice-versa.
 */
data class MyError(val message: String)
data class OtherError(val message: String)
fun MyError.toOtherError(): OtherError = OtherError(message)

context(Raise<MyError>)
fun error(): Nothing =
    raise(MyError("This raise program failed"))

fun left(): Either<MyError, Nothing> =
    either { error() }

/**
 * Transform the Either.Left[MyError] from [left],
 * using [MyError.toOtherError].
 */
fun either(): Either<OtherError, String> =
    // TODO("Transform left() to OtherError")
    left()
        .mapLeft(MyError::toOtherError)

/**
 * Call [error] inside of this function,
 * using [MyError.toOtherError].
 */
context(Raise<OtherError>)
fun raise(): String =
    // TODO("Transform error() to OtherError")
    withError(MyError::toOtherError) {
        error()
    }

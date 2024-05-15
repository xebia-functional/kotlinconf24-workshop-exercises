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
 * Implement `either`, and `dsl` functions,
 * such that they transform `left` and `error` error from [MyError] to [OtherError].
 */
data class MyError(val message: String)
data class OtherError(val message: String)
fun MyError.toOtherError(): OtherError = OtherError(message)

context(Raise<MyError>)
fun error(): Nothing =
    raise(MyError("This raise program failed"))

fun left(): Either<MyError, Nothing> =
    either { error() }

fun either(): Either<OtherError, String> =
    // TODO("Transform left() to OtherError")
    left()
        .mapLeft(MyError::toOtherError)

context(Raise<OtherError>)
fun dsl(): String =
    // TODO("Transform error() to OtherError")
    withError(MyError::toOtherError) {
        error()
    }

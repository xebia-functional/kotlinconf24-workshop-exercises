package com.xebia.com.xebia

import arrow.core.raise.Raise

sealed class Either<out Error, out Success> {
    data class Left<Error>(val error: Error) : Either<Error, Nothing>()
    data class Right<Success>(val value: Success) : Either<Nothing, Success>()
}

fun <Success> Success.right(): Either<Nothing, Success> =
    Either.Right(this)

context(Raise<Error>)
fun <Error, Success> Either<Error, Success>.bind(): Success =
    when(this) {
       is Either.Left -> raise(error)
       is Either.Right -> value
    }


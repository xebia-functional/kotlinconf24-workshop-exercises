package com.xebia.com.xebia.errors.recovering

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.recover
import arrow.core.recover
import arrow.core.raise.either

data class MyError(val message: String)

context(Raise<MyError>)
fun error(): Nothing =
    raise(MyError("This raise program failed"))

fun left(): Either<MyError, Nothing> =
    either { error() }

fun either(): Either<Nothing, String> =
    MyError("This either program failed.").left()
        .recover { "I recovered" }

fun raise(): String =
    recover({ error() }) { "I recovered" }

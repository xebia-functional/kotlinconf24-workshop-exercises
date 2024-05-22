package com.xebia.com.xebia

import arrow.core.Either
import arrow.core.left

data class MyError(val msg: String)

fun left(message: String): Either<MyError, String> =
    MyError(message).left()
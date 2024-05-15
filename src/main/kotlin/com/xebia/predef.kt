package com.xebia.com.xebia

import arrow.core.Either
import arrow.core.left
import com.xebia.com.xebia.errors.recovering.MyError

fun left(message: String): Either<MyError, String> =
    MyError(message).left()
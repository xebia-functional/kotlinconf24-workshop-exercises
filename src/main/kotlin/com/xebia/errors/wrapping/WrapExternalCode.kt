package com.xebia.com.xebia.errors.wrapping

import arrow.core.Either
import arrow.core.raise.Raise
import java.io.IOException
import kotlin.jvm.Throws

/**
 * Exercise: Wrapping SDK
 *
 * We often need to work with 3rd party APIs,
 * and we often want to turn their errors into typed errors.
 *
 * In this exercise we have to wrap [JavaSDK.task],
 * and turn [IOException] into a typed error.
 * As you would've done with checked exceptions in Java.
 *
 * Implement [safeTask] both for Either, and Raise,
 * by capturing [IOException] and turning it into a typed error.
 */
class JavaSDK {
    @Throws(IOException::class)
    fun task(): Unit =
        println("Doing some I/O. Beep Beep.")
}

fun JavaSDK.safeTask(): Either<IOException, Unit> =
    Either.catchOrThrow<IOException, Unit> { task() }
//     try {
//         task().right()
//     } catch(e: IOException) {
//         e.left()
//     }

context(Raise<IOException>)
fun JavaSDK.safeTask(): Unit =
    try {
        task()
    } catch (e: IOException) {
        raise(e)
    }

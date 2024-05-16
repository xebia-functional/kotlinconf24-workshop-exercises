package com.xebia.com.xebia.errors.validation

import arrow.core.NonEmptyList
import arrow.core.raise.Raise
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import arrow.core.raise.zipOrAccumulate

/**
 * Exercises:
 *
 * 1. Implement [Request.id]
 * 2. Implement [Request.email]
 * 3. Implement [Request.user]
 *
 * Additional exercise:
 * Tip: Use what you've learned during domain modelling.
 *
 * 1. [User] suffers from primitive obsession, ids are very sensitive,
 * so we need to make them more typesafe.
 *
 * 2. Emails are sensitive information, make sure they're never logged, or printed.
 */
data class User(val id: Long, val email: String?)

/**
 * Validate that the [Request.pathParams] 'id' exists, or raise an error.
 * If an id exists transform it into a Long, or raise an error if it's not valid.
 */
context(Raise<String>)
fun Request.id(): Long =
    ensureNotNull(pathParams["id"]?.toLongOrNull()) { "Id path param is missing" }

/**
 * Retrieve the optional email from [Request.queryParams],
 * but if it exists validate that it's not empty, and contains '@',
 * if it doesn't raise an appropriate error.
 */
context(Raise<String>)
fun Request.email(): String? =
    queryParams["email"]?.also { email ->
        ensure(email.isNotEmpty()) { "Email cannot be empty" }
        ensure(email.contains("@")) { "Email needs to contain @" }
    }

/**
 * Combine the validated id, and email,
 * and parse them into a user.
 */
context(Raise<NonEmptyList<String>>)
fun Request.user(): User =
    zipOrAccumulate({ id() }, { email() }, ::User)

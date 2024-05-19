package com.xebia.com.xebia.intro

import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import arrow.core.raise.zipOrAccumulate
import java.time.LocalDate
import java.time.Period

@DslMarker
annotation class PeopleDsl

@JvmInline
value class FullName(val value: String) {
    companion object {
        context(Raise<NonEmptyList<String>>)
        fun build(firstName: String?, lastName: String?): FullName {
            return zipOrAccumulate(
                { ensure(!firstName.isNullOrBlank()) { "The first name is missing" } },
                { ensure(!lastName.isNullOrBlank()) { "The last name is missing" } },
            ) { _, _ -> FullName("$firstName $lastName") }
        }
    }
}

context(Raise<String>)
fun validateDate(date: LocalDate?): LocalDate {
    ensure(date != null) { "The birth date is missing" }
    ensure(Period.between(date, LocalDate.now()).years >= 18) { "The person is under 18" }
    return date
}

data class Person(val fullName: FullName, val email: String, val age: Int) {
    class Builder {
        var firstName: String? = null
        var lastName: String? = null
        var email: String? = null
        var birthDate: LocalDate? = null

        context(Raise<NonEmptyList<String>>)
        fun build(): Person {
            zipOrAccumulate(
                { FullName.build(firstName, lastName) },
                { validateDate(birthDate) },
                { ensureNotNull(email) { raise("The email is missing") } }
            ) { fullName, date, validEmail ->
                val age = Period.between(date, LocalDate.now()).years

                return Person(fullName, validEmail, age)
            }
        }
    }
}

@PeopleDsl
inline fun Raise<NonEmptyList<String>>.person(block: Person.Builder.() -> Unit): Person {
    val builder = Person.Builder()

    return builder.apply(block).build()
}

fun main() {
    val john = either {
        person {
            firstName = "John"
            lastName = "Doe"
            email = "john.doe@example.com"
            birthDate = LocalDate.of(1979, 2, 15)
        }
    }

    println(john) // Either.Right(Person(fullName=John Doe, email=john.doe@example.com, age=45))

    val invalid = either {
        person {
            firstName = "John"
            email = "john.doe@example.com"
        }
    }

    println(invalid) // Either.Left(NonEmptyList(The last name is missing, The birth date is missing, The birth date is missing))
}
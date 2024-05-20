package com.xebia.com.xebia.intro

import arrow.core.NonEmptyList
import arrow.core.raise.Raise
import arrow.core.raise.either
import java.time.LocalDate

@DslMarker
annotation class PeopleDsl

@JvmInline
value class FullName(val value: String) {
    companion object {
        context(Raise<NonEmptyList<String>>)
        fun build(firstName: String?, lastName: String?): FullName = TODO()
    }
}

context(Raise<String>)
fun LocalDate?.validateDate(): LocalDate = TODO()

data class Person(val fullName: FullName, val email: String, val age: Int) {
    class Builder {
        var firstName: String? = null
        var lastName: String? = null
        var email: String? = null
        var birthDate: LocalDate? = null

        context(Raise<NonEmptyList<String>>)
        fun build(): Person = TODO()
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
            birthDate = LocalDate.of(2010, 2, 15)
        }
    }

    println(invalid) // Either.Left(NonEmptyList(The last name is missing, The person is under 18))
}
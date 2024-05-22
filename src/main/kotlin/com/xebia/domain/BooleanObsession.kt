package com.xebia.domain

/**
 * Exercise: Boolean Obsession
 *
 * Before you modify code, try to:
 *   - Identify the boolean obsession
 *   - Identify junction in properties
 *   - Identify the potential issues in teamNames
 *
 * Refactor the code to remove Boolean Obsession:
 *   - Split into sealed class
 *   - Refactor teamNames to use when (pattern matching)
 */
data class Employee(
    val name: String,
    val isManager: Boolean,
    val team: List<Employee>? = null
)

fun Employee.teamNames(): List<String> {
    return team?.map { it.name } ?: emptyList()
}

sealed interface Person {
    val name: String

    data class Employee(override val name: String): Person
    data class Manager(override val name: String, val team: List<Employee>): Person
}

fun Person.teamNames(): List<String> {
    return when(this) {
        is Person.Employee -> emptyList()
        is Person.Manager -> team.map { it.name }
    }
}
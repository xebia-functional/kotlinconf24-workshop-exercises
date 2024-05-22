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

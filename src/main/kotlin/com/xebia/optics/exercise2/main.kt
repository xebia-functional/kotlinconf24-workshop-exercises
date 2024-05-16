package com.xebia.com.xebia.optics.lens

import arrow.optics.optics
import com.xebia.com.xebia.optics.exercise1.Person

@optics
data class Person(val name: String, val address: Address) {
    companion object
}

@optics
data class Address(val street: Street, val city: City) {
    companion object
}

@optics
data class Street(val name: String, val number: Int) {
    companion object
}

@optics
data class City(val name: String, val country: String) {
    companion object
}

/**
 * Update `this` [Person] with a [newName],
 * using the Optics DSL
 */
fun Person.changeName(newName: String): Person =
    TODO("Set name to newName")

/**
 * Update `this` [Person.address], [Address.street], [Street.number] with a [newNumber],
 * using the Optics DSL
 */
fun Person.fixStreetNumber(newNumber: Int): Person =
    TODO("Set Person address street number to newNumber")

fun main() {
    val person0 =
        Person(
            "KotlinConf Attendee",
            com.xebia.com.xebia.optics.exercise1.Address(
                com.xebia.com.xebia.optics.exercise1.Street("Center Blvd.", 1),
                com.xebia.com.xebia.optics.exercise1.City("København", "Denmark")
            )
        )

    val person1 = person0.changeName("newName")
    assert(person1.name == "newName")

    val person2 = person1.fixStreetNumber(5)
    assert(person2.address.street.number == 5)
}

package com.xebia.com.xebia.optics.exercise1

import arrow.optics.copy
import arrow.optics.optics

@optics
data class Person(val name: String, val address: Address) {
    companion object
}
@optics data class Address(val street: Street, val city: City) {
    companion object
}
@optics data class Street(val name: String, val number: Int) {
    companion object
}
@optics data class City(val name: String, val country: String) {
    companion object
}

/**
 * Update `this` [Person] with a [newName],
 * using the `copy` method
 */
fun Person.changeName(newName: String): Person =
    copy {
        Person.name.set(newName)
    }

/**
 * Update `this` [Person.address], [Address.street], [Street.number] with a [newNumber],
 * using the `copy` method
 */
fun Person.fixStreetNumber(newNumber: Int): Person =
    Person.address.street.number.set(this, newNumber)

fun main() {
    val person0 =
        Person(
            "KotlinConf Attendee",
            Address(
                Street("Center Blvd.", 1),
                City("København", "Denmark")
            )
        )

    val person1 = person0.changeName("newName")
    assert(person1.name == "newName")

    val person2 = person1.fixStreetNumber(5)
    assert(person2.address.street.number == 5)
}

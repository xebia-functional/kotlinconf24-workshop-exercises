package com.xebia.com.xebia.optics.optional

import arrow.optics.dsl.notNull
import arrow.optics.optics
import com.xebia.com.xebia.optics.street

@optics data class Person(val name: String, val age: Int, val address: Address?) {
    companion object
}
@optics data class Address(val street: Street, val city: City) {
    companion object
}
@optics data class Street(val name: String, val number: Int?) {
    companion object
}
@optics
data class City(val name: String, val country: String) {
    companion object
}

// TODO, fix after Arrow 2 Optics KSP
fun Person.changeStreet(newStreet: Street): Person =
    Person.address.notNull.street.set(this, newStreet)

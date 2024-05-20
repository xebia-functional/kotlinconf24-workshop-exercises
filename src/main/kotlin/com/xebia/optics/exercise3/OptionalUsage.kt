package com.xebia.com.xebia.optics.exercise3

import arrow.optics.optics

@optics
data class Person(val name: String, val age: Int, val address: Address?) {
    companion object
}

@optics
data class Address(val street: Street, val city: City) {
    companion object
}

@optics
data class Street(val name: String, val number: Int?) {
    companion object
}

@optics
data class City(val name: String, val country: String) {
    companion object
}

fun Person.removeAddress(): Person =
    TODO()

fun Person.changeNumber(newNumber: Int): Person =
    TODO()

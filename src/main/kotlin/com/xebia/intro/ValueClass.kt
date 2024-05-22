package com.xebia.com.xebia.intro

interface Simple {
    fun simple(): Unit
}

object StringSimple : Simple {
    override fun simple() =
        println("Hello World")
}

class Id(val value: Long)

@JvmInline
value class BeforeAndAfter(private val simple: Simple): Simple {
    override fun simple() {
      println("before")
      simple.simple()
      println("After")
    }
}

fun main() {
    BeforeAndAfter(StringSimple)
        .simple()
}
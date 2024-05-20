package com.xebia.com.xebia.optics.exercise5

import arrow.optics.Every
import arrow.optics.dsl.every
import io.github.nomisrev.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

fun allCountries(json: String): List<String> {
    TODO()
}

fun incrementMinTemperature(json: String): JsonElement {
    TODO()
}

fun main() {
    println(allCountries(JSON))
    println(incrementMinTemperature(JSON))
}

val JSON = """
    {
      "Description": "Map containing Country, Capital, Currency, and some States of that Country",
      "Region": "Asia",
      "Countries": [
        {
          "Country": "India",
          "Data": {
            "Capital": "New Delhi",
            "mintemp": 6,
            "maxtemp": 45,
            "Currency": "Rupee"
          }
        },
        {
          "Country": "Nepal",
          "Data": {
            "Capital": "Katmandu",
            "mintemp": 9,
            "maxtemp": 23,
            "Currency": "Nepalese rupee"
          }
        }
      ]
    }
""".trimIndent()

package com.xebia.com.xebia.optics.exercise5

import arrow.optics.Every
import arrow.optics.dsl.every
import arrow.optics.dsl.index
import arrow.optics.typeclasses.Index
import io.github.nomisrev.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

// github.com/nomisRev/kotlinx-serialization-jsonpath

fun allCountries(json: String): List<String> {
    val json = Json.decodeFromString<JsonElement>(json)
    return JsonPath
        .select("Countries")
        .select("Country")
        .string
        .getAll(json)
}

fun incrementMinTemperature(json: String): JsonElement {
    val json = Json.decodeFromString<JsonElement>(json)
    return JsonPath
        .path("Countries.*.Data")
        .int
        .modify(json) { it - 1 }
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




fun <A : Any?> Iterable<A>.getOrNullSafe(index: Int): A? =
    TODO()



fun main(): Unit {
    val values = listOf(1, null, 2)
    val x: Int? = values.getOrNull(1)
    val y = listOf(1, 2, 3)
    y.getOrNullSafe(1)
}











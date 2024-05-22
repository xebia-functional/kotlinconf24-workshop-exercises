package com.xebia.com.xebia.optics.exercise3

import arrow.core.Option
import arrow.optics.*
import arrow.optics.dsl.at
import arrow.optics.dsl.notNull
import arrow.optics.dsl.some
import arrow.optics.typeclasses.At
import kotlinx.serialization.json.JsonPrimitive


@optics sealed interface JsonElement {
    companion object
}

@optics data class JsObject(val properties: Map<String, JsonElement>): JsonElement {
    companion object
}
@optics data class JsArray(val array: List<JsonElement>): JsonElement {
    companion object
}


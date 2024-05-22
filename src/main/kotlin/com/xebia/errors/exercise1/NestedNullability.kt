package com.xebia.com.xebia.errors.exercise1

fun <K, V : Any?> Iterable<V>.mapFromChosen(
    predicate: (V) -> Boolean,
    key: (V) -> K
): Map<K, V> =
    mapNotNull { x ->
        if(predicate(x)) {
            val key = key(x)
            Pair(key, x)
        } else null
    }.toMap()

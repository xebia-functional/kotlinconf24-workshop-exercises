package com.xebia.com.xebia.errors.exercise1

fun <K, V> Iterable<V>.mapFromChosen(
  predicate: (V) -> Boolean,
  key: (V) -> K
): Map<K, V> =
  mapNotNull { x ->
    if (predicate(x)) Pair(key(x), x) else null
    // TODO fix failing test
//    x.takeIf(predicate)?.let { Pair(key(it), it) }
  }.toMap()
package com.xebia.com.xebia.intro

interface DataConnection<T> {
    val datasource: MutableMap<Long, T>

    fun getAll(): List<T>
    fun add(item: T)
}

data class Data(val id: Long, val value: String)

class InMemoryDataConnection : DataConnection<Data> {
    override val datasource: MutableMap<Long, Data> = mutableMapOf()

    override fun getAll(): List<Data> {
        return datasource.values.toList()
    }

    override fun add(item: Data) {
        datasource += Pair(item.id, item)
    }
}

fun <T> DataConnection<T>.populateData(data: Map<Long, T>) {
    datasource += data
}

/**
 * In this exercise, we want to ensure that, after creating the connection to the data source, we want to populate
 * it with the initial data. To do so, we can use the function [populateData]. Consider resolve this exercise by
 * using to different scope functions.
 */
fun main() {
    val data = mapOf(Pair(1L, Data(1L, "a")), Pair(2L, Data(2L, "b")))

    val dataConnectionWithPopulatedDataAlternative1 = TODO()

    val dataConnectionWithPopulatedDataAlternative2 = TODO()
}
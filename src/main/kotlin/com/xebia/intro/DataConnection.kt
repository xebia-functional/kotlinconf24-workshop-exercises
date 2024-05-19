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

fun main() {
    val data = mapOf(Pair(1L, Data(1L, "a")), Pair(2L, Data(2L, "b")))

    val dataConnection =
        InMemoryDataConnection().also { it.populateData(data) }

    val alternativeDataConnectionWithApply =
        InMemoryDataConnection().apply { populateData(data) }
}
package com.makentoshe.booruchan.core.network.filter

/** This is a base entry for any filter that will be converts to part of url */
interface FilterEntry {
    val key: String
    val value: String?

    /**
     * This abstraction allows to do not know all available entries,
     * keys for query search and some additional features
     */
    interface Builder<Value> {
        fun build(value: Value): FilterEntry
    }
}

fun filterEntry(key: String, value: String?) = object: FilterEntry {
    override val key = key
    override val value = value
}
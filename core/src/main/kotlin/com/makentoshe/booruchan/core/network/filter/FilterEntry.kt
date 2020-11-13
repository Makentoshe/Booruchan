package com.makentoshe.booruchan.core.network.filter

/** This is a base entry for any filter that will be converts to part of url */
interface FilterEntry {
    val key: String
    val value: String?

    /**
     * This abstraction allows to do not know all available entries,
     * keys for query search and some additional features
     */
    interface Builder {
        fun build(value: String?): FilterEntry
    }
}
package com.makentoshe.booruchan.core.network.filter

/** This filter entry allows to define element count in the request */
class CountFilterEntry(override val key: String, override val value: String?) : FilterEntry {

    /** key example: "limit", "count", and so on */
    class Builder(val key: String) : FilterEntry.Builder<Int> {
        override fun build(value: Int): FilterEntry {
            return CountFilterEntry(key, value.toString())
        }
    }
}

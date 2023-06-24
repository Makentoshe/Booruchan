package com.makentoshe.booruchan.core.network.filter

/** This filter entry allows to define elements with names starts with [value] */
class StartsFilterEntry(override val key: String, override val value: String?): FilterEntry {

    /**
     * Key example: "name_pattern", "search\[name_matches\]", and so on
     *
     * [after] is a string that should be defined for each source separately
     * and allows to configure search.
     */
    class Builder(val key: String, private val after: String): FilterEntry.Builder<String> {
        override fun build(value: String): FilterEntry {
            return ContainsFilterEntry(key, "$value$after")
        }
    }
}
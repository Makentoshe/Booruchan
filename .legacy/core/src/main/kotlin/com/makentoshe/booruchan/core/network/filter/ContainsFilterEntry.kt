package com.makentoshe.booruchan.core.network.filter

/** This filter entry allows to define elements with names contains [value] */
class ContainsFilterEntry(override val key: String, override val value: String?): FilterEntry {

    /**
     * Key example: "name_pattern", "search\[name_matches\]", and so on
     *
     * [before] and [after] is a strings that should be defined for each source separately
     * and allows to configure search.
     */
    class Builder(val key: String, private val before: String, private val after: String): FilterEntry.Builder<String> {
        override fun build(value: String): FilterEntry {
            return ContainsFilterEntry(key, "$before$value$after")
        }
    }
}

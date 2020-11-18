package com.makentoshe.booruchan.core.network.filter

/** This filter entry allows to define tags query in the request */
class TagsFilterEntry(override val key: String, override val value: String?) : FilterEntry {

    /** key example: "tags" */
    class Builder(val key: String) : FilterEntry.Builder {
        override fun build(value: String?) = TagsFilterEntry(key, value)
    }
}

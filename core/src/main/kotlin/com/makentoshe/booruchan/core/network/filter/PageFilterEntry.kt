package com.makentoshe.booruchan.core.network.filter

/** This filter entry allows to define element page in the request */
class PageFilterEntry(override val key: String, override val value: String?) : FilterEntry {

    /** key example: "page", "pid", and so on */
    class Builder(val key: String) : FilterEntry.Builder {
        override fun build(value: String?) = PageFilterEntry(key, value)
    }
}

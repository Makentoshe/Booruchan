package com.makentoshe.booruchan.core.network.filter

import com.makentoshe.booruchan.core.post.Tags

/** This filter entry allows to define tags query in the request */
class TagsFilterEntry(override val key: String, override val value: String?) : FilterEntry {

    /** key example: "tags" */
    class Builder(val key: String) : FilterEntry.Builder<Tags> {
        override fun build(value: Tags?) : TagsFilterEntry {
            val tags = value?.tags?.joinToString("%20") { it.text }
            return TagsFilterEntry(key, tags)
        }
    }
}

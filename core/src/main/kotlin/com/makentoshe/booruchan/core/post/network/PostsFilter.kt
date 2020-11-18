package com.makentoshe.booruchan.core.post.network

import com.makentoshe.booruchan.core.network.Filter
import com.makentoshe.booruchan.core.network.filter.CountFilterEntry
import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.core.network.filter.PageFilterEntry
import com.makentoshe.booruchan.core.network.filter.TagsFilterEntry

abstract class PostsFilter(private val params: List<FilterEntry>) : Filter {

    /**
     * First character of the url.
     *
     * It can be "&" if query should be continued or "?" if should be started.
     */
    protected abstract val firstChar: String

    override fun toUrl(): String {
        if (params.isEmpty()) return ""
        return params.mapIndexed { index, entry ->
            "${if (index == 0) firstChar else "&"}${entry.key}=${entry.value}"
        }.joinToString("")
    }

    abstract class Builder {

        abstract val count: CountFilterEntry.Builder

        abstract val page: PageFilterEntry.Builder

        abstract val tags: TagsFilterEntry.Builder

        abstract fun build(entries: List<FilterEntry>): PostsFilter

        open fun build(vararg entries: FilterEntry) = build(entries.toList())
    }
}
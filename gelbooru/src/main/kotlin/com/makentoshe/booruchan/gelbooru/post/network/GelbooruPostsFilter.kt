package com.makentoshe.booruchan.gelbooru.post.network

import com.makentoshe.booruchan.core.network.filter.CountFilterEntry
import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.core.network.filter.PageFilterEntry
import com.makentoshe.booruchan.core.network.filter.TagsFilterEntry
import com.makentoshe.booruchan.core.post.network.PostsFilter

class GelbooruPostsFilter(entries: List<FilterEntry>) : PostsFilter(entries) {

    override val firstChar: String = "&"

    class Builder : PostsFilter.Builder() {

        override val count: CountFilterEntry.Builder
            get() = CountFilterEntry.Builder("limit")

        override val page: PageFilterEntry.Builder
            get() = PageFilterEntry.Builder("pid")

        override val tags: TagsFilterEntry.Builder
            get() = TagsFilterEntry.Builder("tags")

        override fun build(entries: List<FilterEntry>) = GelbooruPostsFilter(entries)

        override fun build(vararg entries: FilterEntry) = build(entries.toList())
    }
}

package com.makentoshe.booruchan.danbooru.post.network

import com.makentoshe.booruchan.core.network.filter.CountFilterEntry
import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.core.network.filter.PageFilterEntry
import com.makentoshe.booruchan.core.network.filter.TagsFilterEntry
import com.makentoshe.booruchan.core.post.network.PostsFilter

class DanbooruPostsFilter(entries: List<FilterEntry>) : PostsFilter(entries) {

    override val firstChar: String = "?"

    class Builder : PostsFilter.Builder2() {

        override val count: CountFilterEntry.Builder
            get() = CountFilterEntry.Builder("limit")

        override val page: PageFilterEntry.Builder
            get() = PageFilterEntry.Builder("page")

        override val tags: TagsFilterEntry.Builder
            get() = TagsFilterEntry.Builder("tags")

        override val availableEntryBuilders: List<FilterEntry.Builder>
            get() = listOf(count, page, tags)

        override fun build(entries: List<FilterEntry>) = DanbooruPostsFilter(entries)

        override fun build(vararg entries: FilterEntry) = build(entries.toList())
    }
}
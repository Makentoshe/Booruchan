package com.makentoshe.booruchan.gelbooru.tag.network

import com.makentoshe.booruchan.core.network.filter.ContainsFilterEntry
import com.makentoshe.booruchan.core.network.filter.CountFilterEntry
import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.core.network.filter.StartsFilterEntry
import com.makentoshe.booruchan.core.tag.network.TagsFilter

class GelbooruTagsFilter(params: List<FilterEntry>) : TagsFilter(params) {

    override val firstChar = "&"

    class Builder : TagsFilter.Builder() {

        override val count: CountFilterEntry.Builder
            get() = CountFilterEntry.Builder("limit")

        override val contains: ContainsFilterEntry.Builder
            get() = ContainsFilterEntry.Builder("name_pattern", "%25", "%25")

        override val starts: StartsFilterEntry.Builder
            get() = StartsFilterEntry.Builder("name_pattern", "%25")

        override val availableEntryBuilders: List<FilterEntry.Builder>
            get() = listOf(count, contains, starts)

        override fun build(entries: List<FilterEntry>) = GelbooruTagsFilter(entries)

        override fun build(vararg entries: FilterEntry): GelbooruTagsFilter {
            return build(entries.toList())
        }
    }
}

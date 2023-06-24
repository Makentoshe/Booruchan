package com.makentoshe.booruchan.danbooru.tag.network

import com.makentoshe.booruchan.core.network.filter.ContainsFilterEntry
import com.makentoshe.booruchan.core.network.filter.CountFilterEntry
import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.core.network.filter.StartsFilterEntry
import com.makentoshe.booruchan.core.tag.network.TagsFilter

class DanbooruTagsFilter(params: List<FilterEntry>) : TagsFilter(params) {

    override val firstChar = "?"

    class Builder : TagsFilter.Builder() {

        override val count: CountFilterEntry.Builder
            get() = CountFilterEntry.Builder("limit")

        override val contains: ContainsFilterEntry.Builder
            get() = ContainsFilterEntry.Builder("search[name_matches]", "*", "*")

        override val starts: StartsFilterEntry.Builder
            get() = StartsFilterEntry.Builder("search[name_matches]", "*")

        override fun build(entries: List<FilterEntry>) = DanbooruTagsFilter(entries)

        override fun build(vararg entries: FilterEntry): DanbooruTagsFilter {
            return build(entries.toList())
        }
    }
}

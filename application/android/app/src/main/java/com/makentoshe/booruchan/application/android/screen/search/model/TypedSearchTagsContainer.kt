package com.makentoshe.booruchan.application.android.screen.search.model

import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.tag.Type

interface SearchTagsContainer {

    /** These tags weren't applied to the search yet */
    val tags: Set<Text>

    /** These tags were already applied to the search */
    val appliedTags: Set<Text>
}

class TypedSearchTagsContainer(val type: Type): SearchTagsContainer {
    override val tags = HashSet<Text>()
    override val appliedTags = HashSet<Text>()
}
package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.tag.Type
import java.util.*
import kotlin.collections.HashSet

internal interface SearchTagsContainer {

    /** These tags were already applied to the search */
    val tags: Set<Text>

    /** There are actions under the [tags] Collection */
    val tagsActions: List<TagAction>

    sealed class TagAction {

        abstract val tag: Text

        /** [tag] should be removed from the collection */
        internal class Remove(override val tag: Text) : TagAction()

        /** [tag] should be added to the collection */
        internal class Add(override val tag: Text) : TagAction()
    }
}

internal class TypedSearchTagsContainer(val type: Type): SearchTagsContainer {

    override val tags = HashSet<Text>()

    override val tagsActions = LinkedList<SearchTagsContainer.TagAction>()
}
package com.makentoshe.booruchan.model

import com.makentoshe.booruchan.api.component.tag.Tag

/**
 * Storing a set of the tags between fragment recreations while fragment will not completely finished.
 */
interface TagsHolder {
    val set: MutableSet<Tag>
}

class TagsHolderImpl(
    initialSet: Set<Tag>
) : TagsHolder {

    override val set = mutableSetOf<Tag>().apply { addAll(initialSet) }
}

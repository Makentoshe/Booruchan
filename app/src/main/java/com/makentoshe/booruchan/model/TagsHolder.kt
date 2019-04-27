package com.makentoshe.booruchan.model

import com.makentoshe.booruchan.api.component.tag.Tag

/**
 * Storing a tags of the tags between fragment recreations while fragment will not completely finished.
 */
interface TagsHolder {
    val tags: MutableSet<Tag>
}

class TagsHolderImpl(
    initialSet: Set<Tag>
) : TagsHolder {

    override val tags = mutableSetOf<Tag>().apply { addAll(initialSet) }
}

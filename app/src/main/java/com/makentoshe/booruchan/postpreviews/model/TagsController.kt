package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.booruapi.Tag

interface TagsController {
    fun addTag(tag: Tag)
    fun removeTag(tag: Tag)
    fun subscribeOnChange(action: (Tag, Boolean) -> Unit)
    val currentlySelectedTags: Set<Tag>
}
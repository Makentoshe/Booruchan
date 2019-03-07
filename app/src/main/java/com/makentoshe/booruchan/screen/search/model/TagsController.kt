package com.makentoshe.booruchan.screen.search.model

import com.makentoshe.booruapi.Tag
import java.io.Serializable

interface TagsController: Serializable {
    val tags: Set<Tag>
    fun addTag(tag: Tag)
    fun removeTag(tag: Tag)
}
package com.makentoshe.booruchan.screen.posts.model

import com.makentoshe.booruapi.Tag

class TagsControllerImpl: TagsController {

    override val tags = HashSet<Tag>()

    override fun addTag(tag: Tag) {
        tags.add(tag)
    }

    override fun removeTag(tag: Tag) {
        tags.remove(tag)
    }
}
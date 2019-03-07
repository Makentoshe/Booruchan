package com.makentoshe.booruchan.screen.search.model

import com.makentoshe.booruapi.Tag
import io.reactivex.subjects.PublishSubject

class TagsControllerImpl: TagsController {

    override val tags = HashSet<Tag>()

    override fun addTag(tag: Tag) {
        tags.add(tag)
    }

    override fun removeTag(tag: Tag) {
        tags.remove(tag)
    }
}
package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag

class TagRepository(private val booru: Booru) : Repository<String, Tag> {
    override fun get(key: String) = booru.tagFactory.build(key)
}
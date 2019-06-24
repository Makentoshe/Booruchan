package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.gelbooru.Gelbooru
import com.makentoshe.boorulibrary.booru.safebooru.Safebooru

class BooruRepository : Repository<Any, List<Booru>> {

    override fun get(key: Any): List<Booru> {
        return listOf(Gelbooru(), Safebooru())
    }
}
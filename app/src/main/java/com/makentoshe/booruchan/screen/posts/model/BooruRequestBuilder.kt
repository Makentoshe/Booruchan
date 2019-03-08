package com.makentoshe.booruchan.screen.posts.model

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag

class BooruRequestBuilder(private val tags: Set<Tag>, private val page: Int) {
    fun build(count: Int): Booru.PostRequest {
        return Booru.PostRequest(count, page, tags)
    }
}
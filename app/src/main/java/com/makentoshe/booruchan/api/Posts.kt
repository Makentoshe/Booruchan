package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag

interface Posts {
    fun request(count: Int, tags: Set<Tag>, page: Int): List<Post>

    fun request(request: Request): List<Post> = request(request.count, request.tags, request.page)

    fun request(postId: Long): Post

    fun setOfTags2String(set: Set<Tag>): String {
        val strTags = StringBuilder()
        set.forEachIndexed { index, tag ->
            strTags.append(tag.title)
            if (index != set.size - 1) strTags.append(" ")
        }
        return strTags.toString()
    }

    data class Request(val count: Int, val tags: Set<Tag>, val page: Int)
}
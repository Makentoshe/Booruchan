package com.makentoshe.booruchan.common.api

import com.makentoshe.booruchan.common.api.entity.Post

class Posts<T: Post>(val count: Int, val offset: Int) {

    private val posts: MutableList<T> = ArrayList()

    fun getPost(index: Int): T {
        return posts[index]
    }

    fun addPost(post: T) {
        posts.add(post)
    }

    fun count(): Int {
        return posts.size
    }
}

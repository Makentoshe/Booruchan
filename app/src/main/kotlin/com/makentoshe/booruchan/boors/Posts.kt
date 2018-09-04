package com.makentoshe.booruchan.boors

import com.makentoshe.booruchan.boors.entity.Post

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

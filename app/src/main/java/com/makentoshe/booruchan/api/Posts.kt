package com.makentoshe.booruchan.api

interface Posts {
    fun request(count: Int, tags: List<Tag>, page: Int): List<Post>
}
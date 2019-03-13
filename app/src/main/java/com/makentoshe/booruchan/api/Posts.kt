package com.makentoshe.booruchan.api

interface Posts {
    fun request(count: Int, tags: Set<Tag>, page: Int): List<Post>

    fun request(request: Request): List<Post>

    data class Request(val count: Int, val tags: Set<Tag>, val page: Int)
}
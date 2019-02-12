package com.makentoshe.booruapi

import java.io.InputStream
import java.io.Serializable

abstract class Booru(protected val api: BooruApi) : Serializable {
    abstract val title: String
    abstract fun customGet(request: String): InputStream
    abstract fun autocomplete(term: String): List<Tag>
    abstract fun getPosts(request: PostRequest): Posts
    abstract fun getPreview(previewUrl: String): InputStream

    data class PostRequest(val count: Int, val page: Int, val tags: Set<Tag>)
}
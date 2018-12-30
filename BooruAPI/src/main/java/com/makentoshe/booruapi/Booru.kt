package com.makentoshe.booruapi

import java.io.InputStream
import java.io.Serializable

abstract class Booru(protected val api: BooruApi): Serializable {
    abstract val title: String
    abstract fun customGet(request: String): InputStream
    abstract fun autocomplete(term: String): List<Tag>
    abstract fun getPosts(count: Int, page: Int, tags: Set<Tag>): Posts
    abstract fun getPreview(previewUrl: String): InputStream
}
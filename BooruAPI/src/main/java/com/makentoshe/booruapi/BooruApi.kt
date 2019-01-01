package com.makentoshe.booruapi

import java.io.Serializable

interface BooruApi: Serializable {
    fun getCustomRequest(request: String): String
    fun getAutocompleteRequest(term: String): String
    fun getPostsRequest(count: Int, page: Int, tags: Set<Tag>): String
    fun getPreviewRequest(previewUrl: String): String
}
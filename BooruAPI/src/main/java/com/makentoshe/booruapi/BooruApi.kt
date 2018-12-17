package com.makentoshe.booruapi

interface BooruApi {
    fun getCustomRequest(request: String): String
    fun getAutocompleteRequest(term: String): String
    fun getPostsRequest(count: Int, page: Int, tags: Set<Tag>): String
    fun getPreviewRequest(previewUrl: String): String
}
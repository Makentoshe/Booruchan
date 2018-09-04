package com.makentoshe.booruchan.boors

interface BoorRequestAPI {

    fun getPostByIdRequest(id: Int): String

    fun getPostsByTagsRequest(limit: Int, tags: String, page: Int): String

    fun getPostViewByIdRequest(id: Int): String

    fun getAutocompleteSearchRequest(term: String): String

    fun getUserProfileViewByIdRequest(id: String): String

    fun getCommentByPostIdRequest(id: Int): String

    fun getCustomRequest(request: String): String
}
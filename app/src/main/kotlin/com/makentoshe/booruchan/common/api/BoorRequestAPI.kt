package com.makentoshe.booruchan.common.api

interface BoorRequestAPI {

    fun getPostByIdRequest(id: Int): String

    fun getPostsByTagsRequest(limit: Int, tags: String, page: Int): String

    fun getPostViewByIdRequest(id: Int): String

    fun getAutocompleteSearchRequest(term: String): String

    fun getUserProfileViewByIdRequest(id: String): String

    fun getCommentsByPostIdRequest(id: Int): String

    fun getCustomRequest(request: String): String

    fun getListOfLastCommentsRequest(): String

    fun getListOfCommentsViewRequest(page: Int): String
}
package com.makentoshe.booruchan.common.api

import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import java.io.Serializable

abstract class BoorNetwork(private val api: BoorRequestAPI) : Serializable {

    abstract fun getPostsByTags(limit: Int, tags: String, page: Int, httpClient: HttpClient,
                                onResult: ((Posts<out Post>) -> Unit))

    abstract fun getAutocompleteSearchVariations(httpClient: HttpClient, term: String): List<String>
//
//    abstract fun getCommentByPostId(id: Int, onResult: (List<Any>) -> Unit)

    abstract fun getListOfLastComments(httpClient: HttpClient, onResult: (List<Comment>) -> (Unit))

    abstract fun getListOfComments(page: Int, httpClient: HttpClient, action: (List<Comment>) -> (Unit))

}
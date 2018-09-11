package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.api.entity.Post
import java.io.Serializable

abstract class BoorNetwork(private val api: BoorRequestAPI): Serializable {

    abstract fun getPostsByTags(limit: Int, tags: String, page: Int, httpClient: HttpClient,
                                onResult: ((Posts<out Post>) -> Unit))

    abstract fun getAutocompleteSearchVariations(httpClient: HttpClient, term: String): List<String>
//
//    abstract fun getCommentByPostId(id: Int, onResult: (List<Any>) -> Unit)

}
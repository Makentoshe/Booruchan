package com.makentoshe.booruchan.boors

import com.makentoshe.booruchan.boors.entity.Post

abstract class BoorNetwork(private val api: BoorRequestAPI) {

    abstract fun getPostsByTags(limit: Int, tags: String, page: Int, httpClient: HttpClient,
                                onResult: ((Posts<out Post>) -> Unit))

    abstract fun getAutocompleteSearchVariations(httpClient: HttpClient, term: String): List<String>
//
//    abstract fun getCommentByPostId(id: Int, onResult: (List<Any>) -> Unit)

}
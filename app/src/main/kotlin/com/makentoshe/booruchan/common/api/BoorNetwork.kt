package com.makentoshe.booruchan.common.api

import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.api.parser.HtmlParser
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import java.io.Serializable

abstract class BoorNetwork(private val api: BoorRequestAPI) : Serializable {

    abstract fun getPostsByTags(limit: Int, tags: String, page: Int, httpClient: HttpClient,
                                onResult: ((Posts<out Post>) -> Unit))

    abstract fun getAutocompleteSearchVariations(httpClient: HttpClient, term: String): List<String>

    abstract suspend fun getPostById(postId: Int, httpClient: HttpClient, action: (Post) -> Unit)

    abstract suspend fun getListOfLastCommentedPosts(page: Int, httpClient: HttpClient)
            : List<Pair<Post, List<Comment>>>
}
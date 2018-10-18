package com.makentoshe.booruchan.common.api

import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.api.parser.HtmlParser
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import java.io.Serializable

abstract class BoorNetwork(private val api: BoorRequestAPI, @JvmField val client: HttpClient) : Serializable {

    abstract suspend fun getAutocompleteSearchVariations(term: String): List<String>

    abstract suspend fun getPostById(postId: Int, httpClient: HttpClient): Post

    abstract suspend fun getListOfLastCommentedPosts(page: Int, httpClient: HttpClient): List<Pair<Post, List<Comment>>>

    abstract suspend fun getPostsByTags(limit: Int, tags: String, page: Int): Posts<out Post>
}
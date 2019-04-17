package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Comments
import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.network.HttpClient

class SafebooruComments(
    private val httpClient: HttpClient,
    private val commentParser: Parser<List<Comment>>
) : Comments{
    override fun request(postId: Long): List<Comment> {
        val url = "https://safebooru.org/index.php?page=dapi&s=comment&q=index&post_id=$postId"
        val response = httpClient.get(url)
        return commentParser.parse(response.stream)
    }
}
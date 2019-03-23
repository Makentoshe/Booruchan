package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class GelbooruPosts(
    private val httpClient: HttpClient,
    private val parser: Parser<List<Post>>
) : Posts, Serializable {

    override fun request(request: Posts.Request): List<Post> {
        return request(request.count, request.tags, request.page)
    }

    override fun request(count: Int, tags: Set<Tag>, page: Int): List<Post> {
        val strTags = StringBuilder()
        tags.forEachIndexed { index, tag ->
            strTags.append(tag.title)
            if (index != tags.size - 1) strTags.append(" ")
        }
        val request = "https://gelbooru.com/index.php?page=dapi&s=post&q=index&limit=$count&pid=$page&tags=$strTags"
        return parser.parse(httpClient.get(request).stream)
    }
}
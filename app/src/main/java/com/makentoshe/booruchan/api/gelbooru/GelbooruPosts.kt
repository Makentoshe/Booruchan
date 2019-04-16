package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class GelbooruPosts(
    private val httpClient: HttpClient,
    private val parser: Parser<List<Post>>
) : Posts, Serializable {

    override fun request(count: Int, tags: Set<Tag>, page: Int): List<Post> {
        val strTags = setOfTags2String(tags)
        val request = "https://gelbooru.com/index.php?page=dapi&s=post&q=index&limit=$count&pid=$page&tags=$strTags"
        return parser.parse(httpClient.get(request).stream)
    }
}
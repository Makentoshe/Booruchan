package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.network.HttpClient

class Rule34Posts(
    private val httpClient: HttpClient,
    private val parser: Parser<List<Post>>
) : Posts {

    override fun request(count: Int, tags: Set<Tag>, page: Int): List<Post> {
        val strTags = setOfTags2String(tags)
        val url = "https://rule34.xxx/index.php?page=dapi&s=post&q=index&limit=$count&pid=$page&tags=$strTags"
        return parser.parse(httpClient.get(url).stream)
    }

}
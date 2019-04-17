package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.*
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.post.PostTagsParser
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Gelbooru(private val httpClient: HttpClient) : Booru, Serializable {

    override val title: String = this::class.java.simpleName

    override fun getCustom(params: Map<String, String>) = GetCustom(httpClient, params)

    override fun headCustom(params: Map<String, String>) = HeadCustom(httpClient, params)

    override fun getAutocomplete() = GelbooruAutocomplete(httpClient, getAutocompleteTagParser())

    override fun getPosts() = GelbooruPosts(httpClient, getPostParser())

    override fun getAutocompleteTagParser() = GelbooruTagParser()

    override fun getPostParser(): Parser<List<Post>> {
        val tagFactory = GelbooruTagFactory()
        val parser = PostTagsParser(tagFactory)
        val postFactory = GelbooruPostFactory(parser)
        return GelbooruPostParserXml(postFactory)
    }

    override fun getComments(): Comments {
        val commentFactory = GelbooruCommentFactory()
        val commentParser = GelbooruCommentParserXml(commentFactory)
        return GelbooruComments(httpClient, commentParser)
    }
}
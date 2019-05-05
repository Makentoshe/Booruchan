package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.*
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.post.PostTagsParser
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Safebooru(private val httpClient: HttpClient) : Booru, Serializable {

    override val title = "Safebooru"

    override val nsfw = false

    override fun getCustom(params: Map<String, String>) = GetCustom(httpClient, params)

    override fun headCustom(params: Map<String, String>) = HeadCustom(httpClient, params)

    override fun getAutocomplete(): Autocomplete {
        return SafebooruAutocomplete(httpClient, getAutocompleteTagParser())
    }

    override fun getPosts(): Posts {
        return SafebooruPosts(httpClient, getPostParser())
    }

    override fun getAutocompleteTagParser(): Parser<List<Tag>> {
        return SafebooruAutocompleteTagParser()
    }

    override fun getPostParser(): Parser<List<Post>> {
        val tagFactory = SafebooruTagFactory()
        val postTagsParser = PostTagsParser(tagFactory)
        val postFactory = SafebooruPostFactory(postTagsParser)
        return SafebooruPostParserXml(postFactory)
    }

    override fun getComments(): Comments {
        val commentFactory = SafebooruCommentFactory()
        val commentParser = SafebooruCommentParserXml(commentFactory)
        return SafebooruComments(httpClient, commentParser)
    }
}
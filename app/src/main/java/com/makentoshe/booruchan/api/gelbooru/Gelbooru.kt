package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Gelbooru(private val httpClient: HttpClient) : Booru, Serializable {

    override val title: String = this::class.java.simpleName

    override fun getCustom() = GelbooruCustom()

    override fun getAutocomplete() = GelbooruAutocomplete(httpClient, getTagParser())

    override fun getPosts() = GelbooruPosts(httpClient, getPostParser())

    override fun getTagParser() = GelbooruTagParser()

    override fun getPostParser() = GelbooruPostParserXml()
}
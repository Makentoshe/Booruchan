package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.*
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Gelbooru(private val httpClient: HttpClient) : Booru, Serializable {

    override fun getAutocomplete() = GelbooruAutocomplete(httpClient, getTagParser())

    override fun getPosts() = GelbooruPosts(httpClient, getPostParser())

    override fun getTagParser() = GelbooruTagParser()

    override fun getPostParser() = GelbooruPostParserXml()
}
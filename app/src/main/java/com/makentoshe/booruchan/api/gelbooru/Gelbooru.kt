package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Gelbooru(private val httpClient: HttpClient) : Booru, Serializable {

    override val title: String = this::class.java.simpleName

    override fun getCustom(params: Map<String, String>) = GelbooruCustom(httpClient, params)

    override fun getAutocomplete() = GelbooruAutocomplete(httpClient, getAutocompleteTagParser())

    override fun getPosts() = GelbooruPosts(httpClient, getPostParser())

    override fun getAutocompleteTagParser() = GelbooruTagParser()

    override fun getPostParser() = GelbooruPostParserXml()
}
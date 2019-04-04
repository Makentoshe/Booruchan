package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.*
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Safebooru(private val httpClient: HttpClient) : Booru, Serializable {

    override val title: String
        get() = "Safebooru"

    override fun getCustom(params: Map<String, String>) = SafebooruGetCustom(httpClient, params)

    override fun headCustom(params: Map<String, String>) = BooruHeadCustom(httpClient, params)

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
        return SafebooruPostParserXml()
    }
}
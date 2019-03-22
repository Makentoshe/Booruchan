package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.*
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Safebooru(private val httpClient: HttpClient) : Booru, Serializable {

    override val title: String
        get() = "Safebooru"

    override fun getCustom(): Custom {
        return SafebooruCustom(httpClient)
    }

    override fun getAutocomplete(): Autocomplete {
        return SafebooruAutocomplete(httpClient, getTagParser())
    }

    override fun getPosts(): Posts {
        return SafebooruPosts(httpClient, getPostParser())
    }

    override fun getTagParser(): Parser<List<Tag>> {
        return SafebooruTagParser()
    }

    override fun getPostParser(): Parser<List<Post>> {
        return SafebooruPostParserXml()
    }
}
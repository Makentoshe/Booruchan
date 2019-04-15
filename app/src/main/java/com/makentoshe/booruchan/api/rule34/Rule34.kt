package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.*
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Rule34(private val httpClient: HttpClient) : Booru, Serializable {
    override val title = "Rule 34"

    override fun getCustom(params: Map<String, String>) = GetCustom(httpClient, params)

    override fun headCustom(params: Map<String, String>) = HeadCustom(httpClient, params)

    override fun getAutocomplete() = Rule34Autocomplete(httpClient, getAutocompleteTagParser())

    override fun getPosts(): Posts {
        TODO("not implemented")
    }

    override fun getAutocompleteTagParser() = Rule34AutocompleteTagParser()

    override fun getPostParser(): Parser<List<Post>> {
        TODO("not implemented")
    }
}
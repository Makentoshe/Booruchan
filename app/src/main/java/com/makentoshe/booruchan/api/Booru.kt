package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult
import java.io.Serializable

interface Booru : Serializable {

    val title: String

    fun getCustom(params: Map<String, String> = mapOf()): Custom

    fun headCustom(params: Map<String, String> = mapOf()): Custom

    fun getAutocomplete(): Autocomplete

    fun getPosts(): Posts

    fun getAutocompleteTagParser(): Parser<List<Tag>>

    fun getPostParser(): Parser<List<Post>>
}

class BooruHeadCustom(private val httpClient: HttpClient, private val params: Map<String, String>) : Custom {
    override fun request(request: String): HttpResult {
        return httpClient.head(request, params)
    }
}
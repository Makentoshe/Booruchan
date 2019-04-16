package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.GetCustom
import com.makentoshe.booruchan.api.HeadCustom
import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.post.PostTagsParser
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class Rule34(private val httpClient: HttpClient) : Booru, Serializable {
    override val title = "Rule 34"

    override fun getCustom(params: Map<String, String>) = GetCustom(httpClient, params)

    override fun headCustom(params: Map<String, String>) = HeadCustom(httpClient, params)

    override fun getAutocomplete() = Rule34Autocomplete(httpClient, getAutocompleteTagParser())

    override fun getPosts() = Rule34Posts(httpClient, getPostParser())

    override fun getAutocompleteTagParser() = Rule34AutocompleteTagParser()

    override fun getPostParser(): Parser<List<Post>> {
        val tagFactory = Rule34TagFactory()
        val parser = PostTagsParser(tagFactory)
        val postFactory = Rule34PostFactory(parser)
        return Rule34PostParserXml(postFactory)
    }
}
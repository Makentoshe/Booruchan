package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Autocomplete
import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class SafebooruAutocomplete(
    private val httpClient: HttpClient,
    private val tagParser: Parser<List<Tag>>
) : Autocomplete, Serializable {
    override fun request(term: String): List<Tag> {
        val url = "https://safebooru.org/index.php?page=tags&s=list&tags=$term*&sort=desc&order_by=index_count"
        return tagParser.parse(httpClient.get(url).stream)
    }
}
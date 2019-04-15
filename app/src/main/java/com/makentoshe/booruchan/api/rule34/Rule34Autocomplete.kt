package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.Autocomplete
import com.makentoshe.booruchan.api.component.parser.Parser
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.network.HttpClient

class Rule34Autocomplete(
    private val httpClient: HttpClient,
    private val tagParser: Parser<List<Tag>>
) : Autocomplete {

    override fun request(term: String): List<Tag> {
        val url = "https://rule34.xxx/autocomplete.php?q=$term"
        val stream = httpClient.get(url).stream
        return tagParser.parse(stream)
    }
}
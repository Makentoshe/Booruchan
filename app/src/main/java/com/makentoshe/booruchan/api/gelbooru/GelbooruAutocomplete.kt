package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Autocomplete
import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class GelbooruAutocomplete(
    private val httpClient: HttpClient,
    private val tagParser: Parser<List<Tag>>
) : Autocomplete, Serializable {

    override fun request(term: String): List<Tag> {
        val stream = httpClient.get("https://gelbooru.com/index.php?page=autocomplete&term=$term").stream
        return tagParser.parse(stream)
    }

}
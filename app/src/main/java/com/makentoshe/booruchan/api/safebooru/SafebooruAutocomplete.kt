package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Autocomplete
import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.network.HttpClient
import java.io.Serializable

class SafebooruAutocomplete(
    private val httpClient: HttpClient,
    private val tagParser: Parser<List<Tag>>
) : Autocomplete, Serializable {
    override fun request(term: String): List<Tag> {
        return emptyList()
    }
}
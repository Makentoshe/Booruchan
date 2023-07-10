package com.makentoshe.booruchan.extension.factory

import com.makentoshe.booruchan.extension.entity.NetworkAutocompleteTag
import com.makentoshe.booruchan.extension.parser.AutoCompleteTagParser
import com.makentoshe.booruchan.feature.NetworkRequest
import com.makentoshe.booruchan.feature.NetworkResponse
import com.makentoshe.booruchan.feature.text

abstract class AutoCompleteTagFactory(
    private val parser: AutoCompleteTagParser,
) {

    /** Creates a request for NetworkRepository which performs exact request to the booru */
    abstract fun buildRequest(request: FetchTagsRequest): NetworkRequest

    fun parseResponse(response: NetworkResponse): List<NetworkAutocompleteTag> {
        return parser.parse(response.content.text)
    }

    data class FetchTagsRequest(
        /** tag that should be autocompleted */
        val tag: String
    )
}
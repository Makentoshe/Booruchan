package com.makentoshe.booruchan.extension.safebooru.factory

import com.makentoshe.booruchan.extension.BooruContext
import com.makentoshe.booruchan.extension.factory.AutoCompleteTagFactory
import com.makentoshe.booruchan.extension.parser.AutoCompleteTagParser
import com.makentoshe.booruchan.feature.NetworkMethod
import com.makentoshe.booruchan.feature.NetworkRequest

class SafebooruAutoCompleteTagFactory(
    private val context: BooruContext,
    parser: AutoCompleteTagParser,
) : AutoCompleteTagFactory(parser = parser) {
    override fun buildRequest(request: FetchTagsRequest) = NetworkRequest(
        method = NetworkMethod.Get,
        url = context.host.plus("/autocomplete.php"),
        parameters = mapOf(
            "q" to request.tag,
        )
    )
}
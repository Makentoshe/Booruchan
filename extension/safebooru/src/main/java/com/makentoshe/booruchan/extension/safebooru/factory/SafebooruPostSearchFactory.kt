package com.makentoshe.booruchan.extension.safebooru.factory

import com.makentoshe.booruchan.extension.BooruContext
import com.makentoshe.booruchan.extension.factory.BooruPostSearchFactory
import com.makentoshe.booruchan.extension.safebooru.parser.SafebooruPostSearchJsonParser
import com.makentoshe.booruchan.feature.NetworkMethod
import com.makentoshe.booruchan.feature.NetworkRequest

class SafebooruPostSearchFactory(
    private val context: BooruContext,
    parser: SafebooruPostSearchJsonParser,
) : BooruPostSearchFactory(parser = parser) {
    override fun buildRequest(request: FetchPostsRequest) = NetworkRequest(
        method = NetworkMethod.Get,
        url = context.host.plus("/index.php"),
        parameters = mapOf(
            "page" to "dapi",
            "s" to "post",
            "q" to "index",
            "json" to "1", // force responding with json instead of xml

            "limit" to request.count.toString(),
            "pid" to request.page.toString(),
            "tags" to request.tags,
        )
    )

}
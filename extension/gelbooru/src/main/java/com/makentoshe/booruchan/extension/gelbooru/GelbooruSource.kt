package com.makentoshe.booruchan.extension.gelbooru

import com.makentoshe.booruchan.extension.BooruContext
import com.makentoshe.booruchan.extension.BooruSource
import com.makentoshe.booruchan.extension.factory.BooruPostSearchFactory
import com.makentoshe.booruchan.feature.NetworkMethod
import com.makentoshe.booruchan.feature.NetworkRequest
import com.makentoshe.booruchan.feature.context.BooruSystem

class GelbooruSource : BooruSource {

    override val id: String
        get() = "gelbooru"

    override val context = BooruContext(
        title = "Gelbooru",
        system = BooruSystem.Gelbooru025System,
        host = "https://gelbooru.com"
    )

    override val postSearchFactory: BooruPostSearchFactory
        get() = object : BooruPostSearchFactory {
            override fun buildRequest(page: Int, tags: String, count: Int): NetworkRequest {
                return NetworkRequest(
                    method = NetworkMethod.Get,
                    url = context.host.plus("/index.php"),
                    parameters = mapOf(
                        "page" to "dapi",
                        "s" to "post",
                        "q" to "index",
                        "json" to "1", // force responding with json instead of xml

                        "limit" to count.toString(),
                        "pid" to page.toString(),
                        "tags" to tags,
                    )
                )
            }
        }
}
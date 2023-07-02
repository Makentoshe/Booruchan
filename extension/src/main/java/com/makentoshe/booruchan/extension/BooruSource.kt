package com.makentoshe.booruchan.extension

import com.makentoshe.booruchan.extension.factory.BooruHealthcheckFactory
import com.makentoshe.booruchan.extension.factory.BooruPostSearchFactory
import com.makentoshe.booruchan.feature.NetworkMethod
import com.makentoshe.booruchan.feature.NetworkRequest

interface BooruSource {

    /** Should be unique for each source */
    val id: String

    val context: BooruContext

    val healthcheckFactory: BooruHealthcheckFactory
        get() = object : BooruHealthcheckFactory {
            override fun buildRequest(): NetworkRequest {
                return NetworkRequest(
                    method = NetworkMethod.Head,
                    url = context.host,
                )
            }
        }

    val postSearchFactory: BooruPostSearchFactory
}


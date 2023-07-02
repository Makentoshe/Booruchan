package com.makentoshe.booruchan.extension

import com.makentoshe.booruchan.extension.healthcheck.BooruHealthcheckFactory
import com.makentoshe.booruchan.feature.NetworkMethod
import com.makentoshe.booruchan.feature.NetworkRequest

interface BooruSource {

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
}


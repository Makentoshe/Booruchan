package com.makentoshe.booruchan.extension.healthcheck

import com.makentoshe.booruchan.feature.NetworkRequest

interface BooruHealthcheckFactory {
    fun buildRequest(): NetworkRequest
}

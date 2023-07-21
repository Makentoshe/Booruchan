package com.makentoshe.booruchan.extension.factory

import com.makentoshe.booruchan.feature.NetworkRequest

interface BooruHealthcheckFactory {
    fun buildRequest(): NetworkRequest
}

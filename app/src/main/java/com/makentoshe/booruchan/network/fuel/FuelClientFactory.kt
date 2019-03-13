package com.makentoshe.booruchan.network.fuel

import com.makentoshe.booruchan.network.HttpClientFactory

class FuelClientFactory(
    private val params: List<Pair<String, Any?>>? = null
): HttpClientFactory {
    override fun buildClient() = FuelHttpClient(params)
}

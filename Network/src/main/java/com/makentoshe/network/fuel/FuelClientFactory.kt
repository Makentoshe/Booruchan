package com.makentoshe.network.fuel

import com.makentoshe.network.HttpClientFactory

class FuelClientFactory(
    private val params: List<Pair<String, Any?>>? = null
): HttpClientFactory {
    override fun buildClient() = FuelHttpClient(params)
}

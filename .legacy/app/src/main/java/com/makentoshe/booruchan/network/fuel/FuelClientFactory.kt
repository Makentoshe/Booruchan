package com.makentoshe.booruchan.network.fuel

import com.makentoshe.booruchan.network.HttpClientFactory

class FuelClientFactory : HttpClientFactory {
    override fun buildClient() = FuelHttpClient()
}

package com.makentoshe.network.fuel

import com.makentoshe.network.HttpClient

class FuelHttpClient(
    val params: List<Pair<String, Any?>>?
): HttpClient() {
    override fun get(url: String) = FuelHttpGet(url, params)

    override fun post(url: String, body: ByteArray) = FuelHttpPost(url, params, body)
}
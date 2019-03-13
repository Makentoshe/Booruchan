package com.makentoshe.booruchan.network.fuel

import com.makentoshe.booruchan.network.HttpClient

class FuelHttpClient(
    val params: List<Pair<String, Any?>>?
): HttpClient() {
    override fun get(url: String) = FuelHttpGet(url, params)

    override fun post(url: String, body: ByteArray) = FuelHttpPost(url, params, body)
}
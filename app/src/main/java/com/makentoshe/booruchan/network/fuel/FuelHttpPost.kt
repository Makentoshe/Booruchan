package com.makentoshe.booruchan.network.fuel

import com.github.kittinunf.fuel.httpPost
import com.makentoshe.booruchan.network.HttpPost

class FuelHttpPost(
    url: String,
    params: List<Pair<String, Any?>>?,
    body: ByteArray
) : HttpPost(url) {

    private val response = url.httpPost(params).body(body).response()

    override fun code() = response.second.statusCode

    override fun message() = response.second.responseMessage

}
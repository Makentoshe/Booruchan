package com.makentoshe.booruchan.network.fuel

import com.github.kittinunf.fuel.httpGet
import com.makentoshe.booruchan.network.HttpGet
import java.io.ByteArrayInputStream
import java.io.InputStream

class FuelHttpGet(
    url: String,
    params: List<Pair<String, Any?>>?
) : HttpGet {

    private val response = url.httpGet(params).response()

    override fun stream(): InputStream {
        return ByteArrayInputStream(response.third.get())
    }

    override fun body() = String(response.third.get())

    override fun code() = response.second.statusCode

    override fun message() = response.second.responseMessage
}
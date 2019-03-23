package com.makentoshe.booruchan.network.fuel

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult

class FuelHttpClient : HttpClient() {

    override fun get(url: String): HttpResult {
        return FuelHttpResult(url.httpGet().response())
    }

    override fun post(url: String, body: ByteArray): HttpResult {
        return FuelHttpResult(url.httpPost().body(body).response())
    }
}
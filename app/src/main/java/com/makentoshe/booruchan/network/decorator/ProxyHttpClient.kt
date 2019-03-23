package com.makentoshe.booruchan.network.decorator

import com.github.kittinunf.fuel.httpPost
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult
import com.makentoshe.booruchan.network.fuel.FuelHttpResult

class ProxyHttpClient(private val client: HttpClient, private val proxy: String) : HttpClient() {
    override fun get(url: String): HttpResult {
        val result = client.get(url)
        return if (!result.isSuccessful) {
            val res = proxy.httpPost(listOf("url" to url)).response()
            FuelHttpResult(res)
        } else result
    }

    override fun post(url: String, body: ByteArray): HttpResult {
        val result = client.post(url, body)
        return if (!result.isSuccessful) {
            val res = "http://service.bypass123.com/index.php".httpPost(listOf("url" to url)).body(body).response()
            FuelHttpResult(res)
        } else result
    }
}
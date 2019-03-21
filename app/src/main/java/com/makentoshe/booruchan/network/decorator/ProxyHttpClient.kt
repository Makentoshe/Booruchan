package com.makentoshe.booruchan.network.decorator

import com.github.kittinunf.fuel.httpPost
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult
import com.makentoshe.booruchan.network.fuel.FuelHttpResult

class ProxyHttpClient(
    private val client: HttpClient,
    private val proxyUrl: String
) : HttpClient() {

    override fun get(url: String): HttpResult {
        return proxyRequest({
            client.get(url)
        }, {
            FuelHttpResult(proxyUrl.httpPost(listOf("url" to url)).response())
        })
    }

    override fun post(url: String, body: ByteArray): HttpResult {
        TODO("not implemented")
    }

    private fun <T> proxyRequest(default: () -> T, action: () -> T): T {
        return try {
            default()
        } catch (e: Exception) {
            try {
                action()
            } catch (ignore: Exception) {
                throw e
            }
        }
    }
}


package com.makentoshe.booruchan.network.decorator

import com.github.kittinunf.fuel.httpPost
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult

class ProxyHttpClient(private val client: HttpClient, private val proxy: String) : HttpClient() {

    override fun get(url: String, params: Map<String, String>): HttpResult {
        val result = client.get(url, params)
        return try {
            if (!result.isSuccessful) get(getRedirectUrl(url)) else result
        } catch (e: Exception) {
            result
        }
    }

    override fun post(url: String, body: ByteArray): HttpResult {
        val result = client.post(url, body)
        return if (!result.isSuccessful) post(getRedirectUrl(url), body) else result
    }

    //perform connection to the proxy and returns a mirror url
    private fun getRedirectUrl(url: String): String {
        val redirectResult = proxy.httpPost(listOf("url" to url))
            .allowRedirects(false).response()
        return redirectResult.second.headers["Location"]!![0]
    }
}
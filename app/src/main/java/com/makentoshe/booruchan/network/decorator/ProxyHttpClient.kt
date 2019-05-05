package com.makentoshe.booruchan.network.decorator

import com.github.kittinunf.fuel.httpPost
import com.makentoshe.booruchan.BuildConfig
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult
import java.util.logging.Level
import java.util.logging.Logger

class ProxyHttpClient(private val client: HttpClient, private val proxy: String) : HttpClient() {

    private val logger = Logger.getLogger(this::class.java.simpleName)

    override fun get(url: String, params: Map<String, String>): HttpResult {
        val result = client.get(url, params)
        return try {
            if (!result.isSuccessful) {
                log(result)
                get(getRedirectUrl(url))
            } else result
        } catch (e: Exception) {
            result
        }
    }

    override fun post(url: String, body: ByteArray): HttpResult {
        val result = client.post(url, body)
        return try {
            if (!result.isSuccessful) {
                log(result)
                post(getRedirectUrl(url), body)
            } else result
        } catch (e: Exception) {
            result
        }
    }

    override fun head(url: String, params: Map<String, String>): HttpResult {
        val result = client.head(url, params)
        return try {
            if (!result.isSuccessful) {
                log(result)
                get(getRedirectUrl(url))
            } else result
        } catch (e: Exception) {
            result
        }
    }

    /**
     * Performs connection to the proxy and returns a mirror url
     */
    private fun getRedirectUrl(url: String): String {
        val redirectResult = proxy.httpPost(listOf("url" to url)).allowRedirects(false).response()
        return redirectResult.second.headers.getValue("Location")[0]
    }

    /**
     * Prints a reason for switching to the proxy. Only for the debug
     */
    private fun log(result: HttpResult) = if (BuildConfig.DEBUG) {
        val message = StringBuilder("Result code is ${result.code}").append("\n")
        message.append("Message: ").append(result.message).append("\n")
        message.append("Url: ").append(result.url).append("\n")
        logger.log(Level.INFO, message.toString())
    } else Unit

}
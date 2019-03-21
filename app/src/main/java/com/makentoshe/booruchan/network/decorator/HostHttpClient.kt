package com.makentoshe.booruchan.network.decorator

import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult

class HostHttpClient(private val httpClient: HttpClient, private val hosts: List<String>) : HttpClient() {

    private var hostIndex = 0
    private var currentHost = hosts[hostIndex]

    override fun get(url: String): HttpResult {
        return httpClient.get(currentHost.plus(url))
    }

    override fun post(url: String, body: ByteArray): HttpResult {
        return httpClient.post(url, body)
    }
}
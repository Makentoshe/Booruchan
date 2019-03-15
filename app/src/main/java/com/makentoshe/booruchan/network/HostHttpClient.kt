package com.makentoshe.booruchan.network

class HostHttpClient(private val httpClient: HttpClient, private val hosts: List<String>) : HttpClient() {

    private var hostIndex = 0
    private var currentHost = hosts[hostIndex]

    override fun get(url: String): HttpGet {
        return httpClient.get(currentHost.plus(url))
    }

    override fun post(url: String, body: ByteArray): HttpPost {
        return httpClient.post(url, body)
    }
}
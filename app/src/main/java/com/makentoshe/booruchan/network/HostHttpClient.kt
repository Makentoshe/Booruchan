package com.makentoshe.booruchan.network

class HostHttpClient(private val httpClient: HttpClient, private val hosts: List<String>) : HttpClient() {

    private var hostIndex = 0
    private var currentHost = hosts[hostIndex]

    override fun get(url: String): HttpGet {
        try {
            val result = httpClient.get(currentHost.plus(url))
            hostIndex = 0
            return result
        } catch (e: Exception) {
            try {
                return requestWithRetries { get(url) }
            } catch (ioobe: IndexOutOfBoundsException) {
                throw e
            }
        }
    }

    private fun <T> requestWithRetries(action: () -> T): T {
        changeCurrentHost()
        return action()
    }

    private fun changeCurrentHost() {
        hostIndex++
        currentHost = hosts[hostIndex]
    }


    override fun post(url: String, body: ByteArray): HttpPost {
        try {
            val result = httpClient.post(currentHost.plus(url), body)
            hostIndex = 0
            return result
        } catch (e: Exception) {
            try {
                return requestWithRetries { post(url, body) }
            } catch (ioobe: IndexOutOfBoundsException) {
                throw e
            }
        }
    }
}
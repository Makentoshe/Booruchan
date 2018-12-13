package com.makentoshe.network

interface HttpClientFactory {
    fun buildClient(): HttpClient
}
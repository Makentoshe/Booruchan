package com.makentoshe.booruchan.network

interface HttpClientFactory {
    fun buildClient(): HttpClient
}
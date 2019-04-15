package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult

class HeadCustom(private val httpClient: HttpClient, private val params: Map<String, String>) :
    Custom {
    override fun request(request: String): HttpResult {
        return httpClient.head(request, params)
    }
}
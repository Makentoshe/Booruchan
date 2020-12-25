package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult

class GetCustom(private val httpClient: HttpClient, private val params: Map<String, String>) : Custom {
    override fun request(request: String): HttpResult {
        return httpClient.get(request, params)
    }
}
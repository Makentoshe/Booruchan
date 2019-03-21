package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Custom
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.fuel.FuelClientFactory
import java.io.InputStream

class SafebooruCustom(private val httpClient: HttpClient = FuelClientFactory().buildClient()) : Custom {
    override fun request(request: String): InputStream {
        return httpClient.get(request).stream()
    }
}
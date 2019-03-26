package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Custom
import com.makentoshe.booruchan.network.HttpClient
import java.io.InputStream

class SafebooruCustom(private val httpClient: HttpClient) : Custom {
    override fun request(request: String): InputStream {
        return httpClient.get(request).stream
    }
}
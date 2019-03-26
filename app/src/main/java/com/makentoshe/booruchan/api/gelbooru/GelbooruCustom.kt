package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Custom
import com.makentoshe.booruchan.network.HttpClient
import java.io.InputStream

class GelbooruCustom(private val httpClient: HttpClient) : Custom {
    override fun request(request: String): InputStream {
        return httpClient.get(request).stream
    }
}
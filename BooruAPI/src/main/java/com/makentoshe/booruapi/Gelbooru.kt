package com.makentoshe.booruapi

import com.makentoshe.network.HttpClient
import java.io.InputStream


class GelbooruApi: BooruApi {
    override fun getCustomRequest(request: String): String {
        return "https://gelbooru.com$request"
    }
}

class Gelbooru(private val httpClient: HttpClient): Booru(GelbooruApi()){
    override fun customGetRequest(request: String): InputStream {
        return httpClient.get(api.getCustomRequest(request)).stream()
    }
}
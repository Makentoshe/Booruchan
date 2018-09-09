package com.makentoshe.booruchan.api

import com.github.kevinsawicki.http.HttpRequest

class HttpClient {

    fun get(url: String): HttpRequest {
        return HttpRequest.get(url)
    }

}
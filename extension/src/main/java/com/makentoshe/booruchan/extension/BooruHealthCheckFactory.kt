package com.makentoshe.booruchan.extension

interface BooruHealthCheckFactory {
    fun buildRequest(): Request

    data class Request(
        val host: String,
    )
}


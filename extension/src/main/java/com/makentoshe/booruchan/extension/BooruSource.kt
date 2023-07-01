package com.makentoshe.booruchan.extension

interface BooruSource {
    val context: BooruContext

    val healthCheckFactory: BooruHealthCheckFactory
        get() = object : BooruHealthCheckFactory {
            override fun buildRequest(): BooruHealthCheckFactory.Request {
                return BooruHealthCheckFactory.Request(context.host)
            }
        }
}


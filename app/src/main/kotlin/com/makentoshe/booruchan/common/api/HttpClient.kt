package com.makentoshe.booruchan.common.api

import awaitByteArrayResult
import awaitStringResult
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import kotlinx.coroutines.experimental.runBlocking
import java.io.ByteArrayInputStream
import java.io.InputStream

class HttpClient {

    fun get(url: String): HttpGet {
        return HttpGet(url)
    }

    class HttpGet(url: String) {

        private val client: Request by lazy { Fuel.get(url) }

        suspend fun stream(): InputStream = ByteArrayInputStream(client.awaitByteArrayResult().get())

        fun streamBlocking(): InputStream = runBlocking { stream() }

        suspend fun body(): String = client.awaitStringResult().get()

        fun bodyBlocking(): String = runBlocking { body() }
    }

}
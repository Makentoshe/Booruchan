package com.makentoshe.booruapi

import com.makentoshe.network.HttpClient
import com.makentoshe.network.HttpGet
import com.makentoshe.network.HttpPost
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream

class GelbooruTest {
    private lateinit var booru: Booru

    @Before
    fun init() {
        booru = Gelbooru(GelbooruHttpClient())
    }

    @Test
    fun `should create custom get request`() {
        assertEquals(
            "https://gelbooru.com/request",
            String(booru.customGetRequest("/request").readBytes())
        )
    }
}

internal class GelbooruHttpClient: HttpClient() {
    override fun get(url: String): HttpGet {
        return object : HttpGet(url) {
            private val curl = url
            override fun code() = 3939

            override fun message() = "Mocked"

            override fun stream(): InputStream {
                return ByteArrayInputStream(body().toByteArray())
            }

            override fun body(): String {
                return curl
            }
        }
    }

    override fun post(url: String, body: ByteArray): HttpPost {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
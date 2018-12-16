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

    @Test
    fun `should create custom get request`() {
        val booru = Gelbooru(object : HttpClient() {
            override fun get(url: String) = object : HttpGet(url) {
                override fun stream() = ByteArrayInputStream(body().toByteArray())
                override fun body() = "[\"blush\",\"blue_eyes\",\"blonde_hair\",\"black_hair\",\"blue_hair\"," +
                        "\"black_legwear\",\"black_eyes\",\"black_gloves\",\"blood\",\"blunt_bangs\"]"
                override fun code() = TODO("not implemented")
                override fun message() = TODO("not implemented")
            }

            override fun post(url: String, body: ByteArray) = TODO("not implemented")
        })

        val tags = booru.autocomplete("bl")

        assertEquals(10, tags.size)
    }

    @Test
    fun `should return autocompleted list of tags`() {
        val booru = Gelbooru(object : HttpClient() {
            override fun get(url: String) = object : HttpGet(url) {
                override fun stream() = ByteArrayInputStream(body().toByteArray())
                override fun body() = url
                override fun code() = TODO("not implemented")
                override fun message() = TODO("not implemented")
            }

            override fun post(url: String, body: ByteArray) = TODO("not implemented")
        })

        assertEquals(
            "https://gelbooru.com/request",
            String(booru.customGet("/request").readBytes())
        )
    }
}

package com.makentoshe.booruapi

import com.makentoshe.network.HttpClient
import com.makentoshe.network.HttpGet
import com.makentoshe.network.HttpPost
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*

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
    fun `should return posts`() {
        val booru = Gelbooru(object : HttpClient() {
            override fun get(url: String) = object : HttpGet(url) {
                override fun stream(): InputStream {
                    val data =
                        "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>" + "<posts count=\"4250254\" offset=\"0\">\n" +
                                "<post height=\"1126\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/b7/9f/b79f382b93b8a3290d5d2fec57d26680.jpeg\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/samples/b7/9f/sample_b79f382b93b8a3290d5d2fec57d26680.jpg\" sample_width=\"850\" sample_height=\"798\" preview_url=\"https://simg3.gelbooru.com/thumbnails/b7/9f/thumbnail_b79f382b93b8a3290d5d2fec57d26680.jpg\" rating=\"q\" tags=\"1boy 1girl age_difference breast_sucking breasts cleavage handjob huge_breasts large_breasts nursing_handjob seirinpaul shota smile\" id=\"4527913\" width=\"1200\" change=\"1544970945\" md5=\"b79f382b93b8a3290d5d2fec57d26680\" creator_id=\"336651\" has_children=\"false\" created_at=\"Sun Dec 16 08:35:44 -0600 2018\" status=\"active\" source=\"https://twitter.com/sweetberry334/status/1073848399248539654\" has_notes=\"false\" has_comments=\"false\" preview_width=\"150\" preview_height=\"140\"/>\n" +
                                "</posts>"
                    return ByteArrayInputStream(data.toByteArray())
                }

                override fun body() = TODO("not implemented")
                override fun code() = TODO("not implemented")
                override fun message() = TODO("not implemented")

            }

            override fun post(url: String, body: ByteArray) = TODO("not implemented")
        })
        //mocked method
        val posts = booru.getPosts(0, 1, setOf())
        assertEquals(1, posts.size)
        assertEquals(4250254, posts.count)
    }

    @Test
    fun `should return preview`() {
        val booru = Gelbooru(object : HttpClient() {
            override fun get(url: String) = object : HttpGet(url) {
                override fun stream(): InputStream {
                    return ByteArrayInputStream(url.toByteArray())
                }

                override fun body() = TODO("not implemented")
                override fun code() = TODO("not implemented")
                override fun message() = TODO("not implemented")
            }

            override fun post(url: String, body: ByteArray) = TODO("not implemented")
        })

        assertEquals(
            Arrays.toString("mocked".toByteArray()),
            Arrays.toString(booru.getPreview("mocked").readBytes())
        )
    }
}

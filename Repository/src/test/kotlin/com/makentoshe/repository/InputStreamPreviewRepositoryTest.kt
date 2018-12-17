package com.makentoshe.repository

import com.makentoshe.booruapi.Gelbooru
import com.makentoshe.network.HttpClient
import com.makentoshe.network.HttpGet
import com.makentoshe.repository.cache.CacheImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*

class InputStreamPreviewRepositoryTest {
    private lateinit var repository: Repository<String, InputStream>

    @Before
    fun init() {
        val booru = Gelbooru(object : HttpClient() {
            override fun get(url: String) = object : HttpGet(url) {
                override fun stream() = ByteArrayInputStream(url.toByteArray())
                override fun body() = TODO("not implemented")
                override fun code() = TODO("not implemented")
                override fun message() = TODO("not implemented")
            }

            override fun post(url: String, body: ByteArray) = TODO("not implemented")

        })
        repository = InputStreamPreviewRepository(booru, CacheImpl(2))
    }

    @Test
    fun `should return non-null input stream`() {
        val stream = repository.get("sas")
        assertNotNull(stream)
        assertEquals(Arrays.toString("sas".toByteArray()), Arrays.toString(stream!!.readBytes()))
    }
}
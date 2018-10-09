package com.makentoshe.booruchan.common.api

import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HttpClientTest {

    @Test
    fun `get method should return HttpGet instance`() = runBlocking {
        val client = HttpClient().get("https://www.google.com/")
        assertNotNull(client)
    }

}
package com.makentoshe.booruchan.boors

import com.github.kevinsawicki.http.HttpRequest
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HttpClientTest {

    @Test
    fun `get method should return HttpRequest instance`() {
        val client = HttpClient().get("https://www.google.com/")
        assertEquals(HttpRequest::class.java, client.javaClass)
    }

}
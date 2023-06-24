package com.makentoshe.booruchan.network

import com.makentoshe.booruchan.network.decorator.HostHttpClient
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class HostHttpClientTest {

    @Test
    fun `should works when host is ok`() {
        val mHttpGet = mockk<HttpResult>()
        every { mHttpGet.body } returns "Success"

        val mHttpClient = mockk<HttpClient>()
        every { mHttpClient.get("https://test.com/request") } returns mHttpGet

        val client = HostHttpClient(
            mHttpClient,
            listOf("https://test.com", "https://anothertest.com")
        )
        assertEquals("Success", client.get("/request").body)
    }

    @Test
    fun `should work even first host is not ok`() {
        val mHttpGet = mockk<HttpResult>()
        every { mHttpGet.body } returns "Success"

        val mHttpClient = mockk<HttpClient>()
        every { mHttpClient.get("https://test.com/request") } throws Exception()
        every { mHttpClient.get("https://anothertest.com/request") } returns mHttpGet

        val client = HostHttpClient(
            mHttpClient,
            listOf("https://test.com", "https://anothertest.com")
        )
        assertEquals("Success", client.get("/request").body)
    }

    @Test(expected = Exception::class)
    fun `should throw exception if all hosts is not ok`() {
        val mHttpClient = mockk<HttpClient>()
        every { mHttpClient.get("https://test.com/request") } throws Exception()
        every { mHttpClient.get("https://anothertest.com/request") } throws Exception()

        val client = HostHttpClient(
            mHttpClient,
            listOf("https://test.com", "https://anothertest.com")
        )
        client.get("/request").body
    }
}
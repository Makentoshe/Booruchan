package com.makentoshe.booruchan.booru.content.model

import com.makentoshe.booruchan.common.api.HttpClient
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.stream.Collectors


@RunWith(JUnit4::class)
class DownloaderTest {

    @Test
    fun `downloader should load data from url and return stream`() = runBlocking {
        val string = "Test string"
        val url = "Mocked url"
        val client = mockk<HttpClient>()
        every { client.get(url).stream() } returns ByteArrayInputStream(string.toByteArray())
        Downloader(client).download(url) {
            val resultString = BufferedReader(InputStreamReader(it)).use {
                br -> br.lines().collect(Collectors.joining(System.lineSeparator()))
            }
            assertEquals(string, resultString)
        }.join()
    }

}
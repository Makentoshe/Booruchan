package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpGet
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader

class GelbooruPostsTest {

    @Test
    fun `should return list of posts`() {
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\gelbooru\\XmlPosts"
        val file = File(File("").absolutePath, path)
        val str = FileReader(file).readText()

        val mHttpGet = mockk<HttpGet>()
        every { mHttpGet.stream() } returns ByteArrayInputStream(str.toByteArray())

        val mClient = mockk<HttpClient>()
        every { mClient.get(any()) } returns mHttpGet

        val autocomplete = GelbooruPosts(mClient, GelbooruPostParserXml())
        val list = autocomplete.request(10, setOf(), 1)

        assertEquals(5, list.size)
    }
}
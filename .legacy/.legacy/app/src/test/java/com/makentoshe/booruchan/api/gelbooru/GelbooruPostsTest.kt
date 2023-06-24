package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader
import java.io.InputStream

class GelbooruPostsTest {

    private lateinit var response: InputStream
    private lateinit var booru: Booru

    @Before
    fun init() {
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\gelbooru\\XmlPosts"
        val file = File(File("").absolutePath, path)
        response = ByteArrayInputStream(FileReader(file).readText().toByteArray())

        val mHttpGet = mockk<HttpResult>()
        every { mHttpGet.stream } returns response

        val mClient = mockk<HttpClient>()
        every { mClient.get(any()) } returns mHttpGet

        booru = Gelbooru(mClient)
    }

    @Test
    fun `should return list of posts`() {
        val posts = booru.getPosts()
        val list = posts.request(10, setOf(), 1)
        assertEquals(5, list.size)
    }
}
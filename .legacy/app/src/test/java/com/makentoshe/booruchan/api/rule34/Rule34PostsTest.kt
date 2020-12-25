package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.Posts
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

class Rule34PostsTest {

    private lateinit var posts: Posts
    private lateinit var response: InputStream
    @Before
    fun init() {
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\rule34\\XmlPosts"
        val file = File(File("").absolutePath, path)
        response = ByteArrayInputStream(FileReader(file).readText().toByteArray())

        val result = mockk<HttpResult>()
        every { result.stream } returns response

        val mClient = mockk<HttpClient>()
        every { mClient.get(any()) } returns result

        val parser = Rule34(mClient).getPostParser()

        posts = Rule34Posts(mClient, parser)
    }

    @Test
    fun `should return posts list`() {
        val result = posts.request(3, setOf(), 0)

        assertEquals(3, result.size)
    }

}
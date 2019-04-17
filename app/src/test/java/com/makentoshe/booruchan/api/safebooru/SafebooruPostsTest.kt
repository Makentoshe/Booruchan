package com.makentoshe.booruchan.api.safebooru

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

class SafebooruPostsTest {

    private lateinit var posts: Posts
    private lateinit var response: InputStream
    @Before
    fun init() {
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\safebooru\\XmlPosts"
        val file = File(File("").absolutePath, path)
        response = ByteArrayInputStream(FileReader(file).readText().toByteArray())

        val result = mockk<HttpResult>()
        every { result.stream } returns response

        val mClient = mockk<HttpClient>()
        every { mClient.get(any()) } returns result

        val parser = Safebooru(mClient).getPostParser()

        posts = SafebooruPosts(mClient, parser)
    }

    @Test
    fun `should return posts list`() {
        val result = posts.request(3, setOf(), 0)

        assertEquals(3, result.size)
    }

    @Test
    fun `should return post by id`() {
        val post = posts.request(10)
        assertEquals(2817059L, post.id)
    }
}
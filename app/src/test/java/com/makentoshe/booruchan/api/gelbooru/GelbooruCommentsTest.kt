package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Comments
import com.makentoshe.booruchan.network.HttpClient
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader

class GelbooruCommentsTest {

    private lateinit var comments: Comments

    @Before
    fun init() {
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\gelbooru\\XmlComments"
        val file = File(File("").absolutePath, path)
        val response = ByteArrayInputStream(FileReader(file).readText().toByteArray())

        val mHttpClient = mockk<HttpClient>()
        every { mHttpClient.get(any()).stream } returns response

        val parser = GelbooruCommentParserXml(GelbooruCommentFactory())

        comments = GelbooruComments(mHttpClient, parser)
    }

    @Test
    fun `should create request and return list of posts`() {
        val list = comments.request(123L)

        assertEquals(2, list.size)
    }
}
package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.post.Post
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader
import java.io.InputStream

class Rule34PostParserXmlTest {

    private lateinit var parser: Parser<List<Post>>
    private lateinit var response: InputStream

    @Before
    fun init() {
        val booru = Rule34(mockk())
        parser = booru.getPostParser()

        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\rule34\\XmlPosts"
        val file = File(File("").absolutePath, path)
        response = ByteArrayInputStream(FileReader(file).readText().toByteArray())
    }

    @Test
    fun `should parse xml to posts`() {
        val result = parser.parse(response)

        assertEquals(3, result.size)
        assertEquals(3177214L, result[0].id)
    }

}
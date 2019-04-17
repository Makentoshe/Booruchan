package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.comment.Comment
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader
import java.io.InputStream

class SafebooruCommentParserXmlTest {

    private lateinit var parser: Parser<List<Comment>>
    private lateinit var response: InputStream

    @Before
    fun init() {
        val factory = SafebooruCommentFactory()
        parser = SafebooruCommentParserXml(factory)

        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\safebooru\\XmlComments"
        val file = File(File("").absolutePath, path)
        response = ByteArrayInputStream(FileReader(file).readText().toByteArray())
    }

    @Test
    fun `should parse xml`() {
        val result = parser.parse(response)

        assertEquals(5, result.size)
        assertEquals(2640653L, result[0].postId)
        assertEquals(18215L, result[1].id)
    }
}
package com.makentoshe.booruchan.common.api.parser

import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.ByteArrayInputStream
import java.text.ParseException

@RunWith(JUnit4::class)
class CommentParserTest {

    private val xml = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>" +
            "<comments type=\"array\">\n" +
            "<comment created_at=\"2018-09-21 11:33\" post_id=\"4412644\" body=\"source or link to video plz!! T.T\" creator=\"Anonymous\" id=\"2283115\" creator_id=\"9455\"/>\n" +
            "<comment created_at=\"2018-09-21 11:32\" post_id=\"4412646\" body=\"source or link to video plz!! T.T\" creator=\"Anonymous\" id=\"2283114\" creator_id=\"9455\"/>\n" +
            "<comment created_at=\"2018-09-21 11:21\" post_id=\"3967713\" body=\"Nah, it's just \"close up\".\" creator=\"Anonymous\" id=\"2283112\" creator_id=\"9455\"/>" +
            "</comments>"

    @Test
    fun `parse gelbooru comment from xml`() {
        val parser = CommentParser(Gelbooru.Comment::class.java)
        val comments = parser.parseComments(ByteArrayInputStream(xml.toByteArray()))
        assertNotNull(comments)
        assertEquals(3 ,comments.size)
    }

    @Test(expected = ParseException::class)
    fun `parse gelbooru comment from xml and incorrect data`() {
        val parser = CommentParser(Gelbooru.Comment::class.java)
        val comments = parser.parseComments(ByteArrayInputStream(" sas ".toByteArray()))
        assertNotNull(comments)
        assertEquals(3 ,comments.size)
    }

}
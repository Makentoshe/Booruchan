package com.makentoshe.parser

import com.makentoshe.booruapi.Tag
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class XmlTagParserTest {

    private lateinit var parser: Parser<List<Tag>>

    @Before
    fun init() {
        parser = XmlTagParser()
    }

    @Test
    fun `should parse xml`() {
        val xml = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>" +
                "<tags type=\"array\">\n" +
                "<tag type=\"0\" count=\"27875\" name=\"test\" ambiguous=\"false\" id=\"393939\"/>\n" +
                "<tag type=\"1\" count=\"27875\" name=\"test1\" ambiguous=\"false\" id=\"393938\"/>\n" +
                "<tag type=\"3\" count=\"27875\" name=\"test2\" ambiguous=\"true\" id=\"393937\"/>\n" +
                "</tags>"

        val list = parser.parse(xml)
        val tag = list[0]

        assertEquals(3, list.size)
        assertEquals(27875, tag.count)
        assertEquals("test", tag.name)
        assertEquals(false, tag.ambiguous)
        assertEquals(393939, tag.id)
        assertEquals(Tag.Type.GENERAL, tag.type)
    }

    @Test
    fun `should correctly get tags types in xml`() {
        val xml = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>" +
                "<tags type=\"array\">\n" +
                "<tag type=\"0\" count=\"27875\" name=\"test\" ambiguous=\"false\" id=\"393939\"/>\n" +
                "<tag type=\"1\" count=\"27875\" name=\"test1\" ambiguous=\"false\" id=\"393938\"/>\n" +
                "<tag type=\"2\" count=\"27875\" name=\"test1\" ambiguous=\"false\" id=\"393938\"/>\n" +
                "<tag type=\"3\" count=\"27875\" name=\"test2\" ambiguous=\"true\" id=\"393937\"/>\n" +
                "<tag type=\"4\" count=\"27875\" name=\"test1\" ambiguous=\"false\" id=\"393938\"/>\n" +
                "<tag type=\"5\" count=\"27875\" name=\"test1\" ambiguous=\"false\" id=\"393938\"/>\n" +
                "<tag type=\"6\" count=\"27875\" name=\"test1\" ambiguous=\"false\" id=\"393938\"/>\n" +
                "</tags>"

        val list = parser.parse(xml)
        
        assertEquals(Tag.Type.GENERAL, list[0].type)
        assertEquals(Tag.Type.ARTIST, list[1].type)
//        assertEquals(Tag.Type.GENERAL, list[2].type)
        assertEquals(Tag.Type.COPYRIGHT, list[3].type)
        assertEquals(Tag.Type.CHARACTER, list[4].type)
        assertEquals(Tag.Type.METADATA, list[5].type)
        assertEquals(Tag.Type.UNDEFINED, list[6].type)
    }
}
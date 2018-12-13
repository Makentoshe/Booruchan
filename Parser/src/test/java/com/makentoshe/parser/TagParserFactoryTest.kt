package com.makentoshe.parser

import com.makentoshe.booruapi.Tag
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TagParserFactoryTest {

    private lateinit var factory: TagParserFactory

    @Before
    fun init() {
        factory = ParserFactory().buildFactory(ParserType.TAG)
    }

    @Test
    fun `should parse xml`() {
        val xml = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>" +
                "<tags type=\"array\">\n" +
                "<tag type=\"0\" count=\"27875\" name=\"test\" ambiguous=\"false\" id=\"393939\"/>\n" +
                "<tag type=\"1\" count=\"27875\" name=\"test1\" ambiguous=\"false\" id=\"393938\"/>\n" +
                "<tag type=\"3\" count=\"27875\" name=\"test2\" ambiguous=\"true\" id=\"393937\"/>\n" +
                "</tags>"


        val list = factory.buildParser(ParserStyle.XML).parse(xml)
        val tag = list[0]

        assertEquals(3, list.size)
        assertEquals(27875, tag.count)
        assertEquals("test", tag.name)
        assertEquals(false, tag.ambiguous)
        assertEquals(393939, tag.id)
        assertEquals(Tag.Type.GENERAL, tag.type)
    }

    @Test
    fun `should parse json`() {
        val json = "[{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"character\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14089\",\"tag\":\"test1\",\"count\":\"77312\",\"type\":\"artist\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14088\",\"tag\":\"test2\",\"count\":\"77312\",\"type\":\"tag\",\"ambiguous\":\"0\"}]"

        val list = factory.buildParser(ParserStyle.JSON).parse(json)
        val tag = list[0]

        assertEquals(3, list.size)
        assertEquals(77312, tag.count)
        assertEquals("test", tag.name)
        assertEquals(true, tag.ambiguous)
        assertEquals(14087, tag.id)
        assertEquals(Tag.Type.CHARACTER, tag.type)
    }

    @Test
    fun `should parse autocomplete tags`() {
        val json = "[\"blush\",\"blue_eyes\",\"blonde_hair\",\"black_hair\",\"blue_hair\"," +
                "\"black_legwear\",\"black_eyes\",\"black_gloves\",\"blood\",\"blunt_bangs\"]"

        val list = factory.buildParser(ParserStyle.JSON).parse(json)
        assertEquals(10, list.size)
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

        val list = factory.buildParser(ParserStyle.XML).parse(xml)
        assertEquals(Tag.Type.GENERAL, list[0].type)
        assertEquals(Tag.Type.ARTIST, list[1].type)
//        assertEquals(Tag.Type.GENERAL, list[2].type)
        assertEquals(Tag.Type.COPYRIGHT, list[3].type)
        assertEquals(Tag.Type.CHARACTER, list[4].type)
        assertEquals(Tag.Type.METADATA, list[5].type)
        assertEquals(Tag.Type.UNDEFINED, list[6].type)
    }

    @Test
    fun `should correctly get tags types in json`() {
        val json = "[{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"general\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"artist\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"copyright\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"character\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"metadata\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"somethingelse\",\"ambiguous\":\"1\"}]"

        val list = factory.buildParser(ParserStyle.JSON).parse(json)

        assertEquals(Tag.Type.GENERAL, list[0].type)
        assertEquals(Tag.Type.ARTIST, list[1].type)
        assertEquals(Tag.Type.COPYRIGHT, list[2].type)
        assertEquals(Tag.Type.CHARACTER, list[3].type)
        assertEquals(Tag.Type.METADATA, list[4].type)
        assertEquals(Tag.Type.UNDEFINED, list[5].type)
    }
}
package com.makentoshe.parser

import com.makentoshe.booruapi.Tag
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class JsonTagParserTest {
    private lateinit var parser: Parser<List<Tag>>

    @Before
    fun init() {
        parser = JsonTagParser()
    }

    @Test
    fun `should parse json`() {
        val json = "[{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"character\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14089\",\"tag\":\"test1\",\"count\":\"77312\",\"type\":\"artist\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14088\",\"tag\":\"test2\",\"count\":\"77312\",\"type\":\"tag\",\"ambiguous\":\"0\"}]"

        val list = parser.parse(json)
        val tag = list[0]

        Assert.assertEquals(3, list.size)
        Assert.assertEquals(77312, tag.count)
        Assert.assertEquals("test", tag.name)
        Assert.assertEquals(true, tag.ambiguous)
        Assert.assertEquals(14087, tag.id)
        Assert.assertEquals(Tag.Type.CHARACTER, tag.type)
    }

    @Test
    fun `should parse autocomplete tags`() {
        val json = "[\"blush\",\"blue_eyes\",\"blonde_hair\",\"black_hair\",\"blue_hair\"," +
                "\"black_legwear\",\"black_eyes\",\"black_gloves\",\"blood\",\"blunt_bangs\"]"

        val list = parser.parse(json)
        Assert.assertEquals(10, list.size)
    }


    @Test
    fun `should correctly get tags types in json`() {
        val json = "[{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"general\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"artist\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"copyright\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"character\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"metadata\",\"ambiguous\":\"1\"}," +
                "{\"id\":\"14087\",\"tag\":\"test\",\"count\":\"77312\",\"type\":\"somethingelse\",\"ambiguous\":\"1\"}]"

        val list = parser.parse(json)

        Assert.assertEquals(Tag.Type.GENERAL, list[0].type)
        Assert.assertEquals(Tag.Type.ARTIST, list[1].type)
        Assert.assertEquals(Tag.Type.COPYRIGHT, list[2].type)
        Assert.assertEquals(Tag.Type.CHARACTER, list[3].type)
        Assert.assertEquals(Tag.Type.METADATA, list[4].type)
        Assert.assertEquals(Tag.Type.UNDEFINED, list[5].type)
    }
}
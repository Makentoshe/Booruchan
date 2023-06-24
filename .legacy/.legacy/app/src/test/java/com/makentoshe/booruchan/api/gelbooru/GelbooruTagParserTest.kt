package com.makentoshe.booruchan.api.gelbooru

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.FileReader

class GelbooruTagParserTest {

    private lateinit var parser: GelbooruTagParser

    @Before
    fun init() {
        parser = GelbooruTagParser()
    }

    @Test
    fun `should parse json autocomplete tags`() {
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\gelbooru\\JsonTagsAutocomplete"
        val file = File(File("").absolutePath, path)
        val str = FileReader(file).readText()
        val taglist = parser.parse(str)

        assertEquals(10, taglist.size)
        assertEquals("hat", taglist[0].title)
    }
}
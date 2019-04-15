package com.makentoshe.booruchan.api.rule34

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader
import java.io.InputStream

class Rule34AutocompleteTagParserTest {

    private lateinit var responseStream: InputStream

    @Before
    fun init() {
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\rule34\\JsonTagsAutocomplete"
        val file = File(File("").absolutePath, path)
        responseStream = ByteArrayInputStream(FileReader(file).readText().toByteArray())
    }

    @Test
    fun `should parse json autocomplete tags`() {
        val parser = Rule34AutocompleteTagParser()
        val result = parser.parse(responseStream)

        assertEquals(10, result.size)
        assertEquals("hatsune_miku (6115)", result[0].title)
    }

}
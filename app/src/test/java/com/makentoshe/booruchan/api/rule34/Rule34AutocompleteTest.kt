package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader
import java.io.InputStream

class Rule34AutocompleteTest {

    private lateinit var parser: Parser<List<Tag>>
    private lateinit var response: InputStream

    @Before
    fun init() {
        parser = Rule34AutocompleteTagParser()
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\rule34\\JsonTagsAutocomplete"
        val file = File(File("").absolutePath, path)
        response = ByteArrayInputStream(FileReader(file).readText().toByteArray())
    }

    @Test
    fun `should return list of tags by term`() {
        val mHttpGet = mockk<HttpResult>()
        every { mHttpGet.stream } returns response

        val mClient = mockk<HttpClient>()
        every { mClient.get(any()) } returns mHttpGet

        val autocomplete = Rule34Autocomplete(mClient, parser)
        val list = autocomplete.request("hat")

        Assert.assertEquals(10, list.size)
        Assert.assertEquals("hatsune_miku", list[0].title)
    }

}
package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpResult
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader

class GelbooruAutocompleteTest {

    @Test
    fun `should return list of tags by term`() {
        val path = "\\src\\test\\java\\com\\makentoshe\\booruchan\\api\\gelbooru\\JsonTagsAutocomplete"
        val file = File(File("").absolutePath, path)
        val str = FileReader(file).readText()
        val mHttpGet = mockk<HttpResult>()
        every { mHttpGet.stream } returns ByteArrayInputStream(str.toByteArray())

        val mClient = mockk<HttpClient>()
        every { mClient.get(any()) } returns mHttpGet

        val autocomplete = GelbooruAutocomplete(mClient, GelbooruTagParser())
        val list = autocomplete.request("hat")

        assertEquals(10, list.size)
        assertEquals("hatsune_miku", list[1].title)
    }
}
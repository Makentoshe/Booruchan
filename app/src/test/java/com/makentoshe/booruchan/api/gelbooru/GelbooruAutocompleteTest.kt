package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.HttpGet
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream

class GelbooruAutocompleteTest {

    @Test
    fun `should return list of tags by term`() {
        val str =
            "[\"hat\",\"hatsune_miku\",\"hat_ribbon\",\"hat_bow\",\"hat_removed\",\"hata_no_kokoro\",\"hat_feather\",\"hat_flower\",\"hatsuyuki_(kantai_collection)\",\"hatsune_miku_(append)\"]"

        val mHttpGet = mockk<HttpGet>()
        every { mHttpGet.stream() } returns ByteArrayInputStream(str.toByteArray())

        val mClient = mockk<HttpClient>()
        every { mClient.get(any()) } returns mHttpGet

        val autocomplete = GelbooruAutocomplete(mClient, GelbooruTagParser())
        val list = autocomplete.request("hat")

        assertEquals(10, list.size)
        assertEquals("hatsune_miku", list[1].title)
    }
}
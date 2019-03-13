package com.makentoshe.booruchan.api.gelbooru

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GelbooruTagParserTest {

    private lateinit var parser: GelbooruTagParser

    @Before
    fun init() {
        parser = GelbooruTagParser()
    }

    @Test
    fun `should parse autocomplete tags`() {
        val tags = "[\"hat\",\"hatsune_miku\",\"hat_ribbon\",\"hat_bow\",\"hat_removed\",\"hata_no_kokoro\",\"hat_feather\",\"hat_flower\",\"hatsuyuki_(kantai_collection)\",\"hatsune_miku_(append)\"]"
        val taglist = parser.parse(tags)

        assertEquals(10, taglist.size)
        assertEquals("hat", taglist[0].title)
    }
}
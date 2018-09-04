package com.makentoshe.booruchan.boors

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ParsesTest {

    @Test
    fun `parse Json from autocomplete result`() {
        val json = "[\"hatsune_miku\",\"hatsuyuki_(kantai_collection)\"," +
                "\"hatsune_miku_(append)\",\"hatsune_miku_(cosplay)\"," +
                "\"hatsuzuki_(kantai_collection)\",\"hatsukaze_(kantai_collection)\"," +
                "\"hatsushimo_(kantai_collection)\",\"hatsuharu_(kantai_collection)\"," +
                "\"hatsune_mikuo\",\"hatsuzuki_527\"]"
        val result = parseJsonForAutocomplete(json)
        assertEquals(10, result.size)
        assertEquals("hatsune_miku", result[0])
        assertEquals("hatsuyuki_(kantai_collection)", result[1])
        assertEquals("hatsune_miku_(append)", result[2])
        assertEquals("hatsune_miku_(cosplay)", result[3])
        assertEquals("hatsuzuki_(kantai_collection)", result[4])
        assertEquals("hatsukaze_(kantai_collection)", result[5])
        assertEquals("hatsushimo_(kantai_collection)", result[6])
        assertEquals("hatsuharu_(kantai_collection)", result[7])
        assertEquals("hatsune_mikuo", result[8])
        assertEquals("hatsuzuki_527", result[9])
    }

}
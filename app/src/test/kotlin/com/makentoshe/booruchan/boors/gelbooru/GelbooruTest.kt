package com.makentoshe.booruchan.boors.gelbooru

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test

class GelbooruTest {

    private val instance = Gelbooru()

    @Test
    fun `getApi is not null`() {
        assertNotNull(instance.getApi())
    }

    @Test
    fun `convert time for human`() {
        val localtime = "Sat May 17 17:46:52 -0500 2008"
        val humantime = instance.convertLocalTimeToDefault(localtime)
        val expected = "17 May 2008 in 17:46:52"
        assertEquals(expected, humantime)
    }

    @Test
    fun `get booru name`() {
        val expected = "Gelbooru"
        assertEquals(expected, instance.getBooruName())
    }

//    @Test
//    fun `get autocomplete tips`() = runBlocking {
//        val str = "[\"hatsune_miku\",\"hatsuyuki_(kantai_collection)\",\"hatsune_miku_(append)\",\"hatsune_miku_(cosplay)\",\"hatsuzuki_(kantai_collection)\",\"hatsukaze_(kantai_collection)\",\"hatsushimo_(kantai_collection)\",\"hatsuharu_(kantai_collection)\",\"hatsune_mikuo\",\"hatsuzuki_527\"]"
//
//        println(instance.getAutocompleteSearchVariations("hats"))
//    }

}
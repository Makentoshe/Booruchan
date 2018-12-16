package com.makentoshe.booruapi

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GelbooruApiTest {
    private lateinit var api: BooruApi

    @Before
    fun init() {
        api = GelbooruApi()
    }

    @Test
    fun `should create custom get request`() {
        assertEquals(
            "https://gelbooru.com/request",
            api.getCustomRequest("/request")
        )
    }

    @Test
    fun `should create autocomplete request`() {
        assertEquals(
            "https://gelbooru.com/index.php?page=autocomplete&term=tag",
            api.getAutocompleteRequest("tag")
        )
    }
}
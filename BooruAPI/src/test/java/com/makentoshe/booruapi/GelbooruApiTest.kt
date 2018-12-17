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

    @Test
    fun `should create posts request`() {
        val tags = HashSet<Tag>().apply {
            add(Tag("hatsune_miku"))
            add(Tag("skirt"))
        }
        assertEquals(
            "https://gelbooru.com/index.php?page=dapi&s=post&q=index&limit=3&pid=1&tags=hatsune_miku skirt",
            api.getPostsRequest(3, 1, tags)
        )
    }
}
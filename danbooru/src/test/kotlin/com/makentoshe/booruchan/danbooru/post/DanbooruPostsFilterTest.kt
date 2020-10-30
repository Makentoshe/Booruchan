package com.makentoshe.booruchan.danbooru.post

import com.makentoshe.booruchan.core.post.tagsFromString
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostsFilter
import com.makentoshe.booruchan.danbooru.post.network.JsonDanbooruPostsRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class DanbooruPostsFilterTest {

    @Test
    fun `should properly process filter to the query part of the url`() {
        val filter = DanbooruPostsFilter(count = 10, page = 2, tags = tagsFromString(setOf("1girl", "sky", "sea")))
        val url = JsonDanbooruPostsRequest(filter).url
        assertEquals("https://danbooru.donmai.us/posts.json?limit=10&page=2&tags=1girl%20sky%20sea", url)
    }

}
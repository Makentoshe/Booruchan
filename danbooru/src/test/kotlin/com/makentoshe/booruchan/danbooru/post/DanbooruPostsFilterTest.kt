package com.makentoshe.booruchan.danbooru.post

import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostsFilter
import com.makentoshe.booruchan.danbooru.post.network.JsonDanbooruPostsRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class DanbooruPostsFilterTest {

    @Test
    fun `should properly process filter to the query part of the url`() {
        val filterBuilder = DanbooruPostsFilter.Builder()
        val count = filterBuilder.count.build("10")
        val page = filterBuilder.page.build("2")
        val tags = filterBuilder.tags.build("1girl%20sky%20sea")
        val filter = filterBuilder.build(count, page, tags)
        val url = JsonDanbooruPostsRequest(filter).url
        assertEquals("https://danbooru.donmai.us/posts.json?limit=10&page=2&tags=1girl%20sky%20sea", url)
    }

}
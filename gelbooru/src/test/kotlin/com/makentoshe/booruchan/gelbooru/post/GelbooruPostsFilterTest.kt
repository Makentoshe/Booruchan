package com.makentoshe.booruchan.gelbooru.post

import com.makentoshe.booruchan.core.post.tagsFromString
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsFilter
import com.makentoshe.booruchan.gelbooru.post.network.XmlGelbooruPostsRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class GelbooruPostsFilterTest {

    @Test
    fun `should properly process filter to the query part of the url`() {
        val filter = GelbooruPostsFilter(count = 10, page = 2, tags = tagsFromString(setOf("1girl", "sky", "sea")))
        val url = XmlGelbooruPostsRequest(filter).url
        assertEquals(
            "https://gelbooru.com/index.php?page=dapi&s=post&q=index&limit=10&pid=2&tags=1girl%20sky%20sea",
            url
        )
    }
}
package com.makentoshe.booruchan.gelbooru.tag

import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsFilter
import com.makentoshe.booruchan.gelbooru.tag.network.XmlGelbooruTagsRequest
import org.junit.Assert
import org.junit.Test

class GelbooruTagsFilterTest {

    @Test
    fun `should properly process filter to the query part of the url`() {
        val filter = GelbooruTagsFilter.Builder().build(GelbooruTagsFilter.Builder().count.build(10))
        val url = XmlGelbooruTagsRequest(filter).url
        Assert.assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&limit=10", url)
    }
}

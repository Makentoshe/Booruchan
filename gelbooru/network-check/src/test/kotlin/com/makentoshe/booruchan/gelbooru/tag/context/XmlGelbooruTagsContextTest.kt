package com.makentoshe.booruchan.gelbooru.tag.context

import com.makentoshe.booruchan.gelbooru.tag.context.GelbooruTagsContextTest
import com.makentoshe.booruchan.gelbooru.tag.context.XmlGelbooruTagsContext
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsFilter
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsNetworkManager
import com.makentoshe.booruchan.gelbooru.tag.network.XmlGelbooruTagsRequest

class XmlGelbooruTagsContextTest : GelbooruTagsContextTest() {

    override val context = XmlGelbooruTagsContext { GelbooruTagsNetworkManager(HttpClient()).getTags(it) }

    @Test
    fun `should request xml tags`() = runBlocking {
        val request = XmlGelbooruTagsRequest(GelbooruTagsFilter(count = 5))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}

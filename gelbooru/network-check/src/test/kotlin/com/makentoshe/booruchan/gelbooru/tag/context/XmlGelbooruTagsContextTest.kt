package com.makentoshe.booruchan.gelbooru.tag.context

import GelbooruTagsNetworkManager
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsFilter
import com.makentoshe.booruchan.gelbooru.tag.network.XmlGelbooruTagsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlGelbooruTagsContextTest : GelbooruTagsContextTest() {

    override val context = XmlGelbooruTagsContext { GelbooruTagsNetworkManager(HttpClient()).getTags(it) }

    @Test
    fun `should request xml tags`() = runBlocking {
        val filterEntry = GelbooruTagsFilter.Builder().count.build("5")
        val request = XmlGelbooruTagsRequest(GelbooruTagsFilter(listOf(filterEntry)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}

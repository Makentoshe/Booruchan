package com.makentoshe.booruchan.danbooru.tag.context

import DanbooruTagsNetworkManager
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagsFilter
import com.makentoshe.booruchan.danbooru.tag.network.XmlDanbooruTagsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruTagsContextTest : DanbooruTagsContextTest() {

    override val context = XmlDanbooruTagsContext { DanbooruTagsNetworkManager(HttpClient()).getTags(it) }

    @Test
    fun `should request xml tags`() = runBlocking {
        val countFilterEntry = DanbooruTagsFilter.Builder().count
        val request = XmlDanbooruTagsRequest(DanbooruTagsFilter(listOf(countFilterEntry.build(5))))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags.xml?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}

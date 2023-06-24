package com.makentoshe.booruchan.danbooru.tag.context

import DanbooruTagsNetworkManager
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagsFilter
import com.makentoshe.booruchan.danbooru.tag.network.JsonDanbooruTagsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruTagsContextTest: DanbooruTagsContextTest() {

    override val context = JsonDanbooruTagsContext { DanbooruTagsNetworkManager(HttpClient()).getTags(it) }

    @Test
    fun `should request json tags`() = runBlocking {
        val countFilterEntry = DanbooruTagsFilter.Builder().count
        val request = JsonDanbooruTagsRequest(DanbooruTagsFilter(listOf(countFilterEntry.build(5))))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags.json?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
package com.makentoshe.booruchan.gelbooru.tag.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsFilter
import GelbooruTagsNetworkManager
import com.makentoshe.booruchan.gelbooru.tag.network.JsonGelbooruTagsRequest

class JsonGelbooruTagsContextTest: GelbooruTagsContextTest() {

    override val context = JsonGelbooruTagsContext { GelbooruTagsNetworkManager(HttpClient()).getTags(it) }

    @Test
    fun `should request json tags`() = runBlocking {
        val request = JsonGelbooruTagsRequest(GelbooruTagsFilter(count = 5))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&json=1&limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
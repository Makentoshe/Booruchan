package com.makentoshe.booruchan.gelbooru.tag.context

import GelbooruTagsNetworkManager
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsFilter
import com.makentoshe.booruchan.gelbooru.tag.network.JsonGelbooruTagsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonGelbooruTagsContextTest: GelbooruTagsContextTest() {

    override val context = JsonGelbooruTagsContext { GelbooruTagsNetworkManager(HttpClient()).getTags(it) }

    @Test
    fun `should request json tags`() = runBlocking {
        val filterEntry = GelbooruTagsFilter.Builder().count.build("5")
        val request = JsonGelbooruTagsRequest(GelbooruTagsFilter(listOf(filterEntry)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&json=1&limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
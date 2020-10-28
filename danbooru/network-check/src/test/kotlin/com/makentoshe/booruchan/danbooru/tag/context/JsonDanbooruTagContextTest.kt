package com.makentoshe.booruchan.danbooru.tag.context

import DanbooruTagNetworkManager
import com.makentoshe.booruchan.core.tag.tagId
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagFilter
import com.makentoshe.booruchan.danbooru.tag.network.JsonDanbooruTagRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruTagContextTest : DanbooruTagContextTest() {

    override val context = JsonDanbooruTagContext { DanbooruTagNetworkManager(HttpClient()).getTag(it) }

    @Test
    fun `should request json tag`() = runBlocking {
        val request = JsonDanbooruTagRequest(DanbooruTagFilter.ById(tagId(1598277)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags/1598277.json", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = result.getOrNull()!!

        assertEquals(1598277, successResult.tag.tagId)
    }

}
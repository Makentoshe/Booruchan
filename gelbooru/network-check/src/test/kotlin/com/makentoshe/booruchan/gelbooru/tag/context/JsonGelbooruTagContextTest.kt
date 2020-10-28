package com.makentoshe.booruchan.gelbooru.tag.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagFilter
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagNetworkManager
import com.makentoshe.booruchan.gelbooru.tag.network.JsonGelbooruTagRequest
import com.makentoshe.booruchan.core.tag.tagId
import com.makentoshe.booruchan.gelbooru.tag.context.GelbooruTagContextTest
import com.makentoshe.booruchan.gelbooru.tag.context.JsonGelbooruTagContext

class JsonGelbooruTagContextTest : GelbooruTagContextTest() {

    override val context = JsonGelbooruTagContext { GelbooruTagNetworkManager(HttpClient()).getTag(it) }

    @Test
    fun `should request json tag`() = runBlocking {
        val request = JsonGelbooruTagRequest(GelbooruTagFilter.ById(tagId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&id=1&json=1", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = result.getOrNull()!!

        assertEquals(1, successResult.tag.tagId)
    }

}
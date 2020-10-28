package com.makentoshe.booruchan.danbooru.tag.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagFilter
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagNetworkManager
import com.makentoshe.booruchan.danbooru.tag.network.XmlDanbooruTagRequest
import com.makentoshe.booruchan.core.tag.tagId
import com.makentoshe.booruchan.danbooru.tag.context.DanbooruTagContextTest
import com.makentoshe.booruchan.danbooru.tag.context.XmlDanbooruTagContext

class XmlDanbooruTagContextTest : DanbooruTagContextTest() {

    override val context = XmlDanbooruTagContext { DanbooruTagNetworkManager(HttpClient()).getTag(it) }

    @Test
    fun `should request xml tag`() = runBlocking {

        val request = XmlDanbooruTagRequest(DanbooruTagFilter.ById(tagId(1598277)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags/1598277.xml", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(1598277, successResult.tag.tagId)
    }

}

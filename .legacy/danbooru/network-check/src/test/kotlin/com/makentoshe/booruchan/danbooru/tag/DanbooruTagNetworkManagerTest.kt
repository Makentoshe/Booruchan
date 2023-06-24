package com.makentoshe.booruchan.danbooru.tag

import DanbooruTagNetworkManager
import com.makentoshe.booruchan.core.tag.tagId
import com.makentoshe.booruchan.danbooru.tag.deserialize.JsonDanbooruTagDeserializer
import com.makentoshe.booruchan.danbooru.tag.deserialize.XmlDanbooruTagDeserializer
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagFilter
import com.makentoshe.booruchan.danbooru.tag.network.JsonDanbooruTagRequest
import com.makentoshe.booruchan.danbooru.tag.network.XmlDanbooruTagRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class DanbooruTagNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tag by id`() = runBlocking {
        val request = JsonDanbooruTagRequest(DanbooruTagFilter.ById(tagId(385430)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags/385430.json", request.url)

        val response = DanbooruTagNetworkManager(HttpClient()).getTag(request)
        logger.info { "Response: $response" }

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = JsonDanbooruTagDeserializer().deserializeTag(response.getOrNull()!!)
        logger.info { "Deserialize: $deserializeResult" }
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(385430, deserializeSuccess.tag.tagId)
    }

    @Test
    fun `should request xml tag by id`() = runBlocking {
        val request = XmlDanbooruTagRequest(DanbooruTagFilter.ById(tagId(385430)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags/385430.xml", request.url)
        val response = DanbooruTagNetworkManager(HttpClient()).getTag(request)
        logger.info { "Response: $response" }

        // deserialize xml and check: was the filter condition satisfied?
        val deserializeResult = XmlDanbooruTagDeserializer().deserializeTag(response.getOrNull()!!)
        logger.info { "Deserialize: $deserializeResult" }
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(385430, deserializeSuccess.tag.tagId)
    }
}

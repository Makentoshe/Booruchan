package com.makentoshe.booruchan.gelbooru.tag

import com.makentoshe.booruchan.core.tag.tagId
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import com.makentoshe.booruchan.gelbooru.tag.deserialize.JsonGelbooruTagDeserializer
import com.makentoshe.booruchan.gelbooru.tag.deserialize.XmlGelbooruTagDeserializer
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagFilter
import GelbooruTagNetworkManager
import com.makentoshe.booruchan.gelbooru.tag.network.JsonGelbooruTagRequest
import com.makentoshe.booruchan.gelbooru.tag.network.XmlGelbooruTagRequest
import java.util.logging.Logger

class GelbooruTagNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(10)

    @Test
    fun `should request json tag by id`() = runBlocking {
        val request = JsonGelbooruTagRequest(GelbooruTagFilter.ById(tagId(3854)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&id=3854&json=1", request.url)
        val response = GelbooruTagNetworkManager(HttpClient()).getTag(request)
        logger.info { "Response: $response" }
        val successResponse = response.getOrNull()!!

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(successResponse)
        logger.info { "Deserialize: $deserialize" }
        assertEquals(3854, deserialize.getOrNull()!!.tag.tagId)
    }

    @Test
    fun `should request xml tag by id`() = runBlocking {
        val request = XmlGelbooruTagRequest(GelbooruTagFilter.ById(tagId(3854)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&id=3854", request.url)
        val response = GelbooruTagNetworkManager(HttpClient()).getTag(request)
        logger.info { "Response: $response" }
        val successResponse = response.getOrNull()!!

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(successResponse)
        logger.info { "Deserialize: $deserialize" }
        assertEquals(3854, deserialize.getOrNull()!!.tag.tagId)
    }
}

package com.makentoshe.booruchan.danbooru.comment

import DanbooruCommentsNetworkManager
import com.makentoshe.booruchan.danbooru.comment.deserialize.JsonDanbooruCommentsDeserializer
import com.makentoshe.booruchan.danbooru.comment.deserialize.XmlDanbooruCommentsDeserializer
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentsFilter
import com.makentoshe.booruchan.danbooru.comment.network.JsonDanbooruCommentsRequest
import com.makentoshe.booruchan.danbooru.comment.network.XmlDanbooruCommentsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class DanbooruCommentsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tags with count param`() = runBlocking {
        val request = JsonDanbooruCommentsRequest(DanbooruCommentsFilter(count = 20))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.json?limit=20&group_by=comment", request.url)

        val networkResult = DanbooruCommentsNetworkManager(HttpClient()).getComments(request)
        logger.info { "Network result: $networkResult" }
        val networkSuccess = networkResult.getOrNull()!!

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = JsonDanbooruCommentsDeserializer().deserializeComments(networkSuccess)
        logger.info { "Deserialize result: $deserializeResult" }
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        logger.info { "Success: ${deserializeSuccess.comments.size}" }
        logger.info { "Failure: ${deserializeSuccess.failures.size}" }
    }

    @Test
    fun `should request xml comments with count param`() = runBlocking {
        val request = XmlDanbooruCommentsRequest(DanbooruCommentsFilter(count = 20))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.xml?limit=20&group_by=comment", request.url)

        val networkResult = DanbooruCommentsNetworkManager(HttpClient()).getComments(request)
        logger.info { "Network result: $networkResult" }
        val networkSuccess = networkResult.getOrNull()!!

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = XmlDanbooruCommentsDeserializer().deserializeComments(networkSuccess)
        logger.info { "Deserialize result: $deserializeResult" }
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.comments.size)
        logger.info { "Success: ${deserializeSuccess.comments.size}" }
        logger.info { "Failure: ${deserializeSuccess.failures.size}" }
    }
}

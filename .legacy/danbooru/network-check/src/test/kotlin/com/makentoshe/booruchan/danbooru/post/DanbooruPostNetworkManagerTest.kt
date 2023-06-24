package com.makentoshe.booruchan.danbooru.post

import DanbooruPostNetworkManager
import com.makentoshe.booruchan.core.post.postId
import com.makentoshe.booruchan.danbooru.post.deserialize.JsonDanbooruPostDeserializer
import com.makentoshe.booruchan.danbooru.post.deserialize.XmlDanbooruPostDeserializer
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostFilter
import com.makentoshe.booruchan.danbooru.post.network.JsonDanbooruPostRequest
import com.makentoshe.booruchan.danbooru.post.network.XmlDanbooruPostRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class DanbooruPostNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json post`() = runBlocking {
        val request = JsonDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.json", request.url)
        val response = DanbooruPostNetworkManager(HttpClient()).getPost(request)
        logger.info { "Response: $response" }

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = JsonDanbooruPostDeserializer().deserializePost(response.getOrNull()!!)
        val deserializeSuccess = deserializeResult.getOrNull()!!
        assertEquals(1, deserializeSuccess.post.postId)
        logger.info { "Post: ${deserializeSuccess.post}" }
    }

    @Test
    fun `should request xml post`() = runBlocking {
        val request = XmlDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.xml", request.url)
        val response = DanbooruPostNetworkManager(HttpClient()).getPost(request)
        logger.info { "Response: $response" }

        // deserialize xml and check: was the filter condition satisfied?
        val deserializeResult = XmlDanbooruPostDeserializer().deserializePost(response.getOrNull()!!)
        val deserializeSuccess = deserializeResult.getOrNull()!!
        assertEquals(1, deserializeSuccess.post.postId)
        logger.info { "Post: ${deserializeSuccess.post}" }
    }

    // todo add tests for id 2
}
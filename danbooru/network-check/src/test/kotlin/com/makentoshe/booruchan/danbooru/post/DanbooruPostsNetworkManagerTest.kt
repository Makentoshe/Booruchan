package com.makentoshe.booruchan.danbooru.post

import DanbooruPostsNetworkManager
import com.makentoshe.booruchan.danbooru.post.deserialize.JsonDanbooruPostsDeserializer
import com.makentoshe.booruchan.danbooru.post.deserialize.XmlDanbooruPostsDeserializer
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostsFilter
import com.makentoshe.booruchan.danbooru.post.network.JsonDanbooruPostsRequest
import com.makentoshe.booruchan.danbooru.post.network.XmlDanbooruPostsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class DanbooruPostsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json posts`() = runBlocking {
        val request = JsonDanbooruPostsRequest(DanbooruPostsFilter(count = 10))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.json?limit=10", request.url)
        val response = DanbooruPostsNetworkManager(HttpClient()).getPosts(request)
        logger.info { "Response: $response" }

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = JsonDanbooruPostsDeserializer().deserializePosts(response.getOrNull()!!)
        val deserializeSuccess = deserializeResult.getOrNull()!!
        assertEquals(10, deserializeSuccess.deserializes.size)
        logger.info { "Success: ${deserializeSuccess.posts.size}" }
        logger.info { "Failure: ${deserializeSuccess.failures.size}" }
    }

    @Test
    fun `should request xml posts`() = runBlocking {
        val request = XmlDanbooruPostsRequest(DanbooruPostsFilter(count = 10))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.xml?limit=10", request.url)
        val response = DanbooruPostsNetworkManager(HttpClient()).getPosts(request)
        logger.info { "Response: $response" }

        // deserialize xml and check: was the filter condition satisfied?
        val deserializeResult = XmlDanbooruPostsDeserializer().deserializePosts(response.getOrNull()!!)
        val deserializeSuccess = deserializeResult.getOrNull()!!
        assertEquals(10, deserializeSuccess.deserializes.size)
        logger.info { "Success: ${deserializeSuccess.posts.size}" }
        logger.info { "Failure: ${deserializeSuccess.failures.size}" }
    }
}
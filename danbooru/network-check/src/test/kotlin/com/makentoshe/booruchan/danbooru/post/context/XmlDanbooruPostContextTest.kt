package com.makentoshe.booruchan.danbooru.post.context

import DanbooruPostNetworkManager
import com.makentoshe.booruchan.core.post.postId
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostFilter
import com.makentoshe.booruchan.danbooru.post.network.XmlDanbooruPostRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

// todo add request post with id 2
class XmlDanbooruPostContextTest : DanbooruPostContextTest() {

    override val context = XmlDanbooruPostContext { DanbooruPostNetworkManager(HttpClient()).getPost(it) }

    @Test
    fun `should request xml post`() = runBlocking {
        val request = XmlDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.xml", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(1, successResult.post.postId)
    }

}

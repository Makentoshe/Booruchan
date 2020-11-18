package com.makentoshe.booruchan.danbooru.post.context

import DanbooruPostsNetworkManager
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostsFilter
import com.makentoshe.booruchan.danbooru.post.network.XmlDanbooruPostsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruPostsContextTest: DanbooruPostsContextTest() {

    override val context = XmlDanbooruPostsContext { DanbooruPostsNetworkManager(HttpClient()).getPosts(it) }

    @Test
    fun `should request xml posts`() = runBlocking {
        val filterBuilder = DanbooruPostsFilter.Builder()
        val count = filterBuilder.count.build("5")
        val request = XmlDanbooruPostsRequest(filterBuilder.build(count))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.xml?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}

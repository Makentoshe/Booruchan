package com.makentoshe.booruchan.gelbooru.post.context

import com.makentoshe.booruchan.gelbooru.post.context.GelbooruPostsContextTest
import com.makentoshe.booruchan.gelbooru.post.context.XmlGelbooruPostsContext
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsFilter
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsNetworkManager
import com.makentoshe.booruchan.gelbooru.post.network.XmlGelbooruPostsRequest

class XmlGelbooruPostsContextTest: GelbooruPostsContextTest() {

    override val context = XmlGelbooruPostsContext { GelbooruPostsNetworkManager(HttpClient()).getPosts(it) }

    @Test
    fun `should request xml posts`() = runBlocking {
        val request = XmlGelbooruPostsRequest(GelbooruPostsFilter(count = 5))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=post&q=index&limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}

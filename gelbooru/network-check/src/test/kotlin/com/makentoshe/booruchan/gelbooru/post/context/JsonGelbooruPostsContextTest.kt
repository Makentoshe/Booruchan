package com.makentoshe.booruchan.gelbooru.post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsFilter
import GelbooruPostsNetworkManager
import com.makentoshe.booruchan.gelbooru.post.network.JsonGelbooruPostsRequest

class JsonGelbooruPostsContextTest: GelbooruPostsContextTest() {

    override val context = JsonGelbooruPostsContext { GelbooruPostsNetworkManager(HttpClient()).getPosts(it) }

    @Test
    fun `should request json posts`() = runBlocking {
        val request = JsonGelbooruPostsRequest(GelbooruPostsFilter(count = 5))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=post&q=index&limit=5&json=1", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
package com.makentoshe.booruchan.danbooru.post.context

import DanbooruPostsNetworkManager
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostsFilter
import com.makentoshe.booruchan.danbooru.post.network.JsonDanbooruPostsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruPostsContextTest: DanbooruPostsContextTest() {

    override val context = JsonDanbooruPostsContext { DanbooruPostsNetworkManager(HttpClient()).getPosts(it) }

    @Test
    fun `should request json posts`() = runBlocking {
        val filterBuilder = DanbooruPostsFilter.Builder()
        val count = filterBuilder.count.build("5")
        val request = JsonDanbooruPostsRequest(filterBuilder.build(count))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.json?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
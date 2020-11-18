package com.makentoshe.booruchan.gelbooru.post.context

import GelbooruPostsNetworkManager
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsFilter
import com.makentoshe.booruchan.gelbooru.post.network.JsonGelbooruPostsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonGelbooruPostsContextTest: GelbooruPostsContextTest() {

    override val context = JsonGelbooruPostsContext { GelbooruPostsNetworkManager(HttpClient()).getPosts(it) }

    @Test
    fun `should request json posts`() = runBlocking {
        val filterBuilder = GelbooruPostsFilter.Builder()
        val count = filterBuilder.count.build("5")
        val request = JsonGelbooruPostsRequest(filterBuilder.build(count))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=post&q=index&limit=5&json=1", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
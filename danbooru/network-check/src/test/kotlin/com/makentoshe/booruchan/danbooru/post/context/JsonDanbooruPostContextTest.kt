package com.makentoshe.booruchan.danbooru.post.context

import DanbooruPostNetworkManager
import com.makentoshe.booruchan.core.post.postId
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostFilter
import com.makentoshe.booruchan.danbooru.post.network.JsonDanbooruPostRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

// todo add request post with id 2
class JsonDanbooruPostContextTest: DanbooruPostContextTest() {

    override val context = JsonDanbooruPostContext { DanbooruPostNetworkManager(HttpClient()).getPost(it) }

    @Test
    fun `should request json post`() = runBlocking {
        val request = JsonDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.json", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = result.getOrNull()!!

        assertEquals(1, successResult.post.postId)
    }

}
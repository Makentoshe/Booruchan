package com.makentoshe.booruchan.danbooru.comment.context

import com.makentoshe.booruchan.danbooru.comment.context.DanbooruCommentsContextTest
import com.makentoshe.booruchan.danbooru.comment.context.JsonDanbooruCommentsContext
import com.makentoshe.booruchan.danbooru.comment.context.XmlDanbooruCommentsContext
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentsFilter
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentsNetworkManager
import com.makentoshe.booruchan.danbooru.comment.network.XmlDanbooruCommentsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruCommentsContextTest : DanbooruCommentsContextTest() {

    override val context = JsonDanbooruCommentsContext { DanbooruCommentsNetworkManager(HttpClient()).getComments(it) }

    @Test
    fun `should request xml comments`() = runBlocking {
        val context = XmlDanbooruCommentsContext { DanbooruCommentsNetworkManager(HttpClient()).getComments(it) }

        val request = XmlDanbooruCommentsRequest(DanbooruCommentsFilter(count = 5))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.xml?limit=5&group_by=comment", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}

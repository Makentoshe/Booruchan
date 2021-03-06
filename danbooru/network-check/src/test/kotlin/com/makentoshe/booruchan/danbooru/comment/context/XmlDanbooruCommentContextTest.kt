package com.makentoshe.booruchan.danbooru.comment.context

import DanbooruCommentNetworkManager
import com.makentoshe.booruchan.core.comment.commentId
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentFilter
import com.makentoshe.booruchan.danbooru.comment.network.XmlDanbooruCommentRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruCommentContextTest : DanbooruCommentContextTest() {

    override val context = XmlDanbooruCommentContext { DanbooruCommentNetworkManager(HttpClient()).getComment(it) }

    @Test
    fun `should request xml comment`() = runBlocking {
        val request = XmlDanbooruCommentRequest(DanbooruCommentFilter.ById(commentId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments/1.xml", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(1, successResult.comment.commentId)
    }

}

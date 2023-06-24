package com.makentoshe.booruchan.gelbooru.comment.context

import com.makentoshe.booruchan.gelbooru.comment.network.GelbooruCommentsFilter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

abstract class GelbooruCommentsContextTest {

    abstract val context: GelbooruCommentsContext<*>

    protected val logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with filter`() {
        val filter = GelbooruCommentsFilter(mapOf("a" to "b", "c" to 13))
        val request = context.buildRequest(filter)

        assertEquals(
            "https://gelbooru.com/index.php?page=dapi&s=comment&q=index&a=b&c=13",
            request.url.replace("&json=1", "")
        )
    }
}
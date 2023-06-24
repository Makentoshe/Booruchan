package com.makentoshe.booruchan.gelbooru.post.context

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostFilter
import com.makentoshe.booruchan.core.post.postId
import java.util.logging.Logger

abstract class GelbooruPostContextTest {

    protected val logger = Logger.getLogger(this.javaClass.simpleName)!!
    protected abstract val context: GelbooruPostContext<*>

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with ById filter`() {
        val filter = GelbooruPostFilter.ById(postId(1))
        val request = context.buildRequest(filter)

        // Regex should fit at least these cases:
        // https://gelbooru.com/index.php?page=dapi&s=post&q=index&json=1&id=1
        // https://gelbooru.com/index.php?page=dapi&s=post&q=index&id=1
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=post&q=index&id=1", request.url.replace("&json=1", ""))
    }
}
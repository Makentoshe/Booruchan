package com.makentoshe.booruchan.danbooru.post.context

import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostFilter
import com.makentoshe.booruchan.core.post.postId
import java.util.logging.Logger

abstract class DanbooruPostContextTest {

    protected val logger = Logger.getLogger(this.javaClass.simpleName)!!
    protected abstract val context: DanbooruPostContext<*>

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with ById filter`() {
        val filter = DanbooruPostFilter.ById(postId(1))
        val request = context.buildRequest(filter)

        // Regex should fit at lease these cases:
        // https://danbooru.donmai.us/posts/1.json
        // https://danbooru.donmai.us/posts/1.xml
        assert(request.url.matches("https://danbooru\\.donmai\\.us/posts/1\\..*".toRegex()))
    }
}
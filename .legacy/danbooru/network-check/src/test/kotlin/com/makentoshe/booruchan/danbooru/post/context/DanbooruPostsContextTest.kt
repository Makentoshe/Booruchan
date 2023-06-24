package com.makentoshe.booruchan.danbooru.post.context

import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostsFilter
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

abstract class DanbooruPostsContextTest {

    protected val logger = Logger.getLogger(this.javaClass.simpleName)!!
    protected abstract val context: DanbooruPostsContext<*>

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with filter`() {
        val entry1 = object: FilterEntry {
            override val key = "a"
            override val value = "b"
        }

        val entry2 = object: FilterEntry {
            override val key = "c"
            override val value = "13"
        }

        val filter = DanbooruPostsFilter(listOf(entry1, entry2))
        val request = context.buildRequest(filter)

        // Regex should fit at lease these cases:
        // https://danbooru.donmai.us/posts.json
        // https://danbooru.donmai.us/posts.xml
        assert(request.url.matches("https://danbooru\\.donmai\\.us/posts\\..*\\?a=b&c=13".toRegex()))
    }
}

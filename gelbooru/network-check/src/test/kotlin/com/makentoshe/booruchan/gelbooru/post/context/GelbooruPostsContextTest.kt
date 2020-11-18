package com.makentoshe.booruchan.gelbooru.post.context

import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsFilter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

abstract class GelbooruPostsContextTest {

    protected val logger = Logger.getLogger(this.javaClass.simpleName)!!
    protected abstract val context: GelbooruPostsContext<*>

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

        val filter = GelbooruPostsFilter(listOf(entry1, entry2))
        val request = context.buildRequest(filter)

        assertEquals("https://gelbooru.com/index.php?page=dapi&s=post&q=index&a=b&c=13", request.url.replace("&json=1", ""))
    }
}

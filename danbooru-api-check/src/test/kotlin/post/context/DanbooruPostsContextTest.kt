package post.context

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.network.DanbooruPostsFilter
import java.util.logging.Logger

abstract class DanbooruPostsContextTest {

    protected val logger = Logger.getLogger(this.javaClass.simpleName)!!
    protected abstract val context: DanbooruPostsContext<*>

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with filter`() {
        val filter = DanbooruPostsFilter(mapOf("a" to "b", "c" to 13))
        val request = context.buildRequest(filter)

        assertEquals("https://danbooru.donmai.us/posts.json?a=b&c=13", request.url)
    }
}


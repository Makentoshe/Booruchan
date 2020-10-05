package post.context

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.network.DanbooruPostFilter
import post.postId
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

        Assert.assertEquals("https://danbooru.donmai.us/post/1.json", request.url)
    }
}
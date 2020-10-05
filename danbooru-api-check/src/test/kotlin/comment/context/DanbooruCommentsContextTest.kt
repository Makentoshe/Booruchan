package comment.context

import comment.network.DanbooruCommentsFilter
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

abstract class DanbooruCommentsContextTest {

    abstract val context: DanbooruCommentsContext<*>

    protected val logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with filter`() {
        val filter = DanbooruCommentsFilter(mapOf("a" to "b", "c" to 13))
        val request = context.buildRequest(filter)

        // Regex should fit at lease these cases:
        // https://danbooru.donmai.us/comments.json
        // https://danbooru.donmai.us/comments.xml
        assert(request.url.matches("https://danbooru\\.donmai\\.us/comments\\..*\\?a=b&c=13".toRegex()))
    }
}
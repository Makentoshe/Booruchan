package tag.context

import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.network.DanbooruTagsFilter
import java.util.logging.Logger

abstract class DanbooruTagsContextTest {

    abstract val context: DanbooruTagsContext<*>

    protected val logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with filter`() {
        val filter = DanbooruTagsFilter(mapOf("a" to "b", "c" to 13))
        val request = context.buildRequest(filter)

        assert(request.url.endsWith("?a=b&c=13"))
    }
}
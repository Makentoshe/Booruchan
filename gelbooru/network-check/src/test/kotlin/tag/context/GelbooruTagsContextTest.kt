package tag.context

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.network.GelbooruTagsFilter
import java.util.logging.Logger

abstract class GelbooruTagsContextTest {

    abstract val context: GelbooruTagsContext<*>

    protected val logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with filter`() {
        val filter = GelbooruTagsFilter(mapOf("a" to "b", "c" to 13))
        val request = context.buildRequest(filter)
        assertEquals(
            "https://gelbooru.com/index.php?page=dapi&s=tag&q=index&a=b&c=13",
            request.url.replace("&json=1", "")
        )
    }
}
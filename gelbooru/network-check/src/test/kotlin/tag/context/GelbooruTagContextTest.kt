package tag.context

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.network.GelbooruTagFilter
import tag.tagId
import java.util.logging.Logger

abstract class GelbooruTagContextTest {

    abstract val context: GelbooruTagContext<*>

    protected val logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with ById filter`() {
        val filter = GelbooruTagFilter.ById(tagId(1))
        val request = context.buildRequest(filter)
        assertEquals(
            "https://gelbooru.com/index.php?page=dapi&s=tag&q=index&id=1",
            request.url.replace("&json=1", "")
        )
    }

    @Test
    fun `should build request with ByName filter`() {
        val filter = GelbooruTagFilter.ByName("hatsune_miku")
        val request = context.buildRequest(filter)
        assertEquals(
            "https://gelbooru.com/index.php?page=dapi&s=tag&q=index&name=hatsune_miku",
            request.url.replace("&json=1", "")
        )
    }
}
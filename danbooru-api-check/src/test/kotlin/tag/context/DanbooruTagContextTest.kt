package tag.context

import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.network.DanbooruTagFilter
import tag.tagId
import java.util.logging.Logger

abstract class DanbooruTagContextTest {

    abstract val context: DanbooruTagContext<*>

    protected val logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with ById filter`() {
        val filter = DanbooruTagFilter.ById(tagId(1))
        val request = context.buildRequest(filter)

        // Regex should fit at lease these cases:
        // https://danbooru.donmai.us/tags/1.json
        // https://danbooru.donmai.us/tags/1.xml
        assert(request.url.matches("https://danbooru\\.donmai\\.us/tags/..*".toRegex()))
    }

    @Test
    fun `should build request with ByName filter`() {
        val filter = DanbooruTagFilter.ByName("hatsune_miku")
        val request = context.buildRequest(filter)

        // Regex should fit at lease these cases:
        // https://danbooru.donmai.us/tags.json?search[name]=hatsune_miku
        // https://danbooru.donmai.us/tags.xml?search[name]=hatsune_miku
        assert(request.url.matches("https://danbooru\\.donmai\\.us/tags\\..*\\?search\\[name]=hatsune_miku".toRegex()))
    }
}
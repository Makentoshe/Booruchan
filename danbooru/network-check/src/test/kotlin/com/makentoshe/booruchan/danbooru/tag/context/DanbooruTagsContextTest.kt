package com.makentoshe.booruchan.danbooru.tag.context

import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagsFilter
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

        // Regex should fit at lease these cases:
        // https://danbooru.donmai.us/tags.json
        // https://danbooru.donmai.us/tags.xml
        assert(request.url.matches("https://danbooru\\.donmai\\.us/tags\\..*\\?a=b&c=13".toRegex()))
    }
}
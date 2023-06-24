package com.makentoshe.booruchan.danbooru.tag.context

import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagsFilter
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

abstract class DanbooruTagsContextTest {

    abstract val context: DanbooruTagsContext<*>

    protected val logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should build request with filter`() {
        val filterEntry1 = object: FilterEntry {
            override val key = "a"
            override val value = "b"
        }
        val filterEntry2 = object: FilterEntry {
            override val key = "c"
            override val value = "13"
        }
        val filter = DanbooruTagsFilter(listOf(filterEntry1, filterEntry2))
        val request = context.buildRequest(filter)

        // Regex should fit at lease these cases:
        // https://danbooru.donmai.us/tags.json
        // https://danbooru.donmai.us/tags.xml
        assert(request.url.matches("https://danbooru\\.donmai\\.us/tags\\..*\\?a=b&c=13".toRegex()))
    }
}
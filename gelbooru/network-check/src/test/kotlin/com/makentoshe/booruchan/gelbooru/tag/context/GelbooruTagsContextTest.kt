package com.makentoshe.booruchan.gelbooru.tag.context

import com.makentoshe.booruchan.core.network.filter.FilterEntry
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsFilter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

abstract class GelbooruTagsContextTest {

    abstract val context: GelbooruTagsContext<*>

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
        val filter = GelbooruTagsFilter(listOf(filterEntry1, filterEntry2))
        val request = context.buildRequest(filter)
        assertEquals(
            "https://gelbooru.com/index.php?page=dapi&s=tag&q=index&a=b&c=13",
            request.url.replace("&json=1", "")
        )
    }
}
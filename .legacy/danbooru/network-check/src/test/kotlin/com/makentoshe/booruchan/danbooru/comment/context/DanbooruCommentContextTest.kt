package com.makentoshe.booruchan.danbooru.comment.context

import org.junit.Rule
import org.junit.rules.Timeout
import java.util.logging.Logger

abstract class DanbooruCommentContextTest {

    abstract val context: DanbooruCommentContext<*>

    protected val logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)
}

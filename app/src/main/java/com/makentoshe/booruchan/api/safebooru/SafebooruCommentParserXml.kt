package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.comment.CommentFactory
import com.makentoshe.booruchan.api.parseAttributesToMap
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class SafebooruCommentParserXml(private val commentFactory: CommentFactory): Parser<List<Comment>> {
    override fun parse(data: String): List<Comment> {
        val root = Jsoup.parse(data).body().child(0)
        return root.childNodes()
            .filter { it is Element }
            .map { parseAttributesToMap(it.attributes()) }
            .map { commentFactory.build(it) }
    }
}
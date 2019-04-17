package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.parseAttributesToMap
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class SafebooruPostParserXml(
    private val postFactory: SafebooruPostFactory
) : Parser<List<Post>> {

    override fun parse(data: String): List<Post> {
        val root = Jsoup.parse(data).body().child(0)
        return root.childNodes()
            .filter { it is Element }
            .map { parseAttributesToMap(it.attributes()) }
            .map { postFactory.build(it) }
    }
}
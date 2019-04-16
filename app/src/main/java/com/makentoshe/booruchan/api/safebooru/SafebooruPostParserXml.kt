package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.post.Post
import org.jsoup.Jsoup
import org.jsoup.nodes.Attributes

class SafebooruPostParserXml(
    private val postFactory: SafebooruPostFactory
) : Parser<List<Post>> {

    override fun parse(data: String): List<Post> {
        val root = Jsoup.parse(data).body().child(0)
        val posts = ArrayList<Post>()
        for (i in 0 until root.childNodeSize() / 2 step 1) {
            val attrs = parseAttributesToMap(root.child(i).attributes())
            val post = postFactory.build(attrs)
            posts.add(post)
        }
        return posts
    }

    private fun parseAttributesToMap(attributes: Attributes): HashMap<String, String> {
        val res = HashMap<String, String>()
        for (attr in attributes) {
            res[attr.key] = attr.value
        }
        return res
    }
}
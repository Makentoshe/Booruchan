package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.Post
import org.jsoup.Jsoup
import org.jsoup.nodes.Attributes

class SafebooruPostParserXml : Parser<List<Post>> {
    override fun parse(data: String): List<Post> {
        val root = Jsoup.parse(data).body().child(0)
//        val (count, offset) = extractData(root.attributes())
        val posts = ArrayList<Post>()
        for (i in 0 until root.childNodeSize() / 2 step 1) {
            posts.add(SafebooruPost(parseAttributesToMap(root.child(i).attributes())))
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
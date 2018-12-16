package com.makentoshe.parser

import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import org.jsoup.Jsoup
import org.jsoup.nodes.Attributes

class XmlPostsParser : Parser<Posts> {
    override fun parse(data: String): Posts {
        val root = Jsoup.parse(data).body().child(0)
        val (count, offset) = extractData(root.attributes())
        val posts = ArrayList<Post>()
        for (i in 0 until root.childNodeSize() / 2 step 1) {
            posts.add(Post(parseAttributesToMap(root.child(i).attributes())))
        }
        return Posts(posts = posts, count = count, offset = offset)
    }

    private fun extractData(attrs: Attributes): Pair<Int, Int> {
        var count = -1
        try {
            count = attrs["count"].toInt()
        } catch (e: Exception) {
        }
        var offset = -1
        try {
            offset = attrs["offset"].toInt()
        } catch (e: Exception) {
        }
        return Pair(count, offset)
    }

    private fun parseAttributesToMap(attributes: Attributes): HashMap<String, String> {
        val res = HashMap<String, String>()
        for (attr in attributes) {
            res[attr.key] = attr.value
        }
        return res
    }
}
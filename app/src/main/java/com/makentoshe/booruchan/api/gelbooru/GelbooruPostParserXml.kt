package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.post.Post
import org.jsoup.Jsoup
import org.jsoup.nodes.Attributes

class GelbooruPostParserXml : Parser<List<Post>> {
    override fun parse(data: String): List<Post> {
        val root = Jsoup.parse(data).body().child(0)
//        val (count, offset) = extractData(root.attributes())
        val posts = ArrayList<Post>()
        for (i in 0 until root.childNodeSize() / 2 step 1) {
            posts.add(GelbooruPost(parseAttributesToMap(root.child(i).attributes())))
        }
        return posts
    }

//    private fun extractData(attrs: Attributes): Pair<Int, Int> {
//        val count = attrs["count"].toInt()
//        val offset = attrs["offset"].toInt()
//        return Pair(count, offset)
//    }

    private fun parseAttributesToMap(attributes: Attributes): HashMap<String, String> {
        val res = HashMap<String, String>()
        for (attr in attributes) {
            res[attr.key] = attr.value
        }
        return res
    }
}
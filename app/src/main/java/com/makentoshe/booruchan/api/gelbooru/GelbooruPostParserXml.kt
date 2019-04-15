package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.post.PostTagsParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Attributes

class GelbooruPostParserXml(private val postTagsParser: PostTagsParser) : Parser<List<Post>> {

    override fun parse(data: String): List<Post> {
        val root = Jsoup.parse(data).body().child(0)
        val posts = ArrayList<Post>()
        for (i in 0 until root.childNodeSize() / 2 step 1) {
            val attrs = parseAttributesToMap(root.child(i).attributes())
            val post = GelbooruPost(postTagsParser, attrs)
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
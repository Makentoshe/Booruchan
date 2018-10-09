package com.makentoshe.booruchan.common.api.parser

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.InputStream
import java.lang.IllegalArgumentException
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

interface HtmlParser {

    fun parse(inputStream: InputStream): Any

    fun parse(string: String): Any

    companion object {

        fun parseComments(inputStream: InputStream, booruClass: Class<out Boor>): ArrayList<Pair<Post, List<Comment>>> {
            val parser = getCommentsParser(booruClass)
            return parser.parse(inputStream) as ArrayList<Pair<Post, List<Comment>>>
        }

        fun parseComments(string: String, booruClass: Class<out Boor>): ArrayList<Pair<Post, List<Comment>>> {
            val parser = getCommentsParser(booruClass)
            return parser.parse(string) as ArrayList<Pair<Post, List<Comment>>>
        }

        private fun getCommentsParser(booruClass: Class<out Boor>): HtmlParser {
            return when (booruClass) {
                Gelbooru::class.java -> GelbooruHtmlCommentParser(Gelbooru.Comment::class.java, Gelbooru.Post::class.java)
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class GelbooruHtmlCommentParser(private val commentClass: Class<out Comment>, private val postClass: Class<out Post>): HtmlParser {

    override fun parse(string: String): List<Pair<Post, List<Comment>>>  {
        val document = Jsoup.parse(string)
        val commentedPostsList = document.select("div#comment-list > .post")
        val list = ArrayList<Pair<Post, List<Comment>>>()
        commentedPostsList.forEach {
            list.add(parseCommentedPost(it))
        }
        return list
    }

    override fun parse(inputStream: InputStream): List<Pair<Post, List<Comment>>> {
        return parse(Scanner(inputStream).useDelimiter("\\A").next())
    }

    private fun parseCommentedPost(element: Element): Pair<Post, List<Comment>> {
        val post = parsePostData(element)
        val commentList = parseComments(element, "234")
        return Pair(post, commentList)
    }

    private fun parseComments(element: Element, postId: String): List<Comment> {
        val commentList = ArrayList<Comment>()
        val commentElements = element.select(".col2 .response-list .post")
        commentElements.forEach {
            commentList.add(parseComment(it, postId))
        }
        return commentList
    }

    private fun parseComment(element: Element, postId: String): Comment {
        val map = HashMap<String, Any>()
        map["post_id"] = postId.toInt()
        map["created_at"] = element.select(".author .date").text()
        map["id"] = element.attr("id").substring(1).toInt()
        map["body"] = element.select(".content .body").text()
        map["creator"] = element.select(".author h6 a").text()
        map["creator_id"] = element.select(".author h6 a").attr("href").substringAfter("id=").toInt()
        return commentClass.getConstructor(Map::class.java).newInstance(map)
    }

    private fun parsePostData(element: Element): Post {
        val map = HashMap<String, String>()
        map["id"] = element.attr("id").substring(1)
        val pattern = Pattern.compile(".*posts\\.(\\w+)\\[\\d+\\] = '(.+)'.*")
        element.child(0).dataNodes()[0].wholeData.split("\n").toList().forEach {
            map.putAll(extractPostData(it, pattern))
        }
        val previewElement = element.select(".col1 img[src]")
        map["preview_url"] = previewElement.attr("src")

        val infos = element.select(".col2 .commentHeader .info")
        map["creator"] = infos.select("a").first().text()
        map["created_at"] = infos.first().text().substring(6)

        val post = postClass.newInstance()
        post.fill(map)

        return post
    }

    private fun extractPostData(target: String, pattern: Pattern): Map<String, String> {
        val matcher = pattern.matcher(target)
        return if (matcher.find()) {
            val map = HashMap<String, String>()
            map[matcher.group(1)] = matcher.group(2)
            return map
        } else {
            emptyMap()
        }
    }

}
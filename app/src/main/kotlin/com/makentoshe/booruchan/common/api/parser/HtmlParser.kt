package com.makentoshe.booruchan.common.api.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

class HtmlParser {

    fun parse(inputStream: InputStream): IntArray {
        val document = Jsoup.parse(Scanner(inputStream).useDelimiter("\\A").next())
        val commentNodes = getCommentNodes(document)
        val postIds = IntArray(commentNodes.size)
        commentNodes.forEachIndexed { index, element ->
            postIds[index] = parseCommentNode(element)
        }
        return postIds
    }

    private fun getCommentNodes(document: Document): List<Element> {
        val postsNodes = document.getElementById("comment-list").getElementsByClass("post")
        val postNodesList = ArrayList<Element>()
        postsNodes.forEach {
            if (it.attr("id").contains("p")) postNodesList.add(it)
        }
        return postNodesList
    }

    private fun parseCommentNode(node: Element): Int {
        return node.attr("id").replace("p", "").toInt()
    }
}


class HtmlParserAlt {

    fun parse(inputStream: InputStream): IntArray {
        val document = Jsoup.parse(Scanner(inputStream).useDelimiter("\\A").next())
        val commentNodes = getCommentNodes(document)
        val postIds = IntArray(commentNodes.size)
        commentNodes.forEachIndexed { index, element ->
            postIds[index] = parseCommentNode(element)
        }
        return postIds
    }

    private fun getCommentNodes(document: Document): List<Element> {
        val postsNodes = document.getElementById("comment-list").getElementsByClass("post")
        val postNodesList = ArrayList<Element>()
        postsNodes.forEach {
            if (it.attr("id").contains("p")) postNodesList.add(it)
        }
        return postNodesList
    }

    private fun parseCommentNode(node: Element): Int {
        val map = HashMap<String, String>()
        val postId = node.attr("id").replace("p", "")
        map["post_id"] = postId

        parsePostSimpleData(node, postId.toInt())
        return postId.toInt()
    }

    private fun parsePostSimpleData(node: Element, postId: Int): Map<String, String> {
        val targetNode = node.child(0).dataNodes()[0]
        val map = HashMap<String, String>()
        val pattern = Pattern.compile(".*posts\\.(\\w+)\\[$postId\\] = '(.+)'.*")
        targetNode.wholeData.split("\n").toList().forEach {
            map.putAll(extractPostData(it, pattern))
        }
        map.putAll(extractPostPreviewData(node.child(1)))
        parseComments(node.child(2))
        return map
    }

    private fun extractPostData(target: String, pattern: Pattern): Map<String, String> {
        val matcher = pattern.matcher(target)
        return if (matcher.find()) {
            val map = HashMap<String, String>()
            map["post_${matcher.group(1)}"] = matcher.group(2)
            return map
        } else {
            emptyMap()
        }
    }

    private fun extractPostPreviewData(node: Element): Map<String, String> {
        val map = HashMap<String, String>()
        val url = node.getElementsByAttributeValue("alt", "thumbnail")[0].attr("src")
        map["post_preview"] = url
        return map
    }

    private fun parseComments(node: Element) {

    }


}
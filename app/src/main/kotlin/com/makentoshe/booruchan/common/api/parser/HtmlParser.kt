package com.makentoshe.booruchan.common.api.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.InputStream
import java.util.*

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
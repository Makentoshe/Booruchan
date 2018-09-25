package com.makentoshe.booruchan.common.api.parser

import com.makentoshe.booruchan.common.api.entity.Comment
import org.jsoup.Jsoup
import org.jsoup.nodes.*
import java.io.InputStream
import java.lang.Exception
import java.text.ParseException
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class CommentParser<T : Comment>(private val clazz: Class<T>) {

    fun parseComments(inputStream: InputStream): List<Comment> {
        val string = Scanner(inputStream).useDelimiter("\\A").next()
        return try {
            parseAsXmlOrHtml(string)
        } catch (e: Exception) {
            throw ParseException("InputStream data is not valid xml format.", 0)
        }
    }

    private fun parseAsXmlOrHtml(string: String): List<Comment> {
        val document = Jsoup.parse(string)
        return when (getDoctype(document).attr("name")) {
            "html" -> HtmlParser().parse(document)
            else -> parseCommentsXml(document)
        }
    }

    private fun getDoctype(document: Document): DocumentType {
        for (node in document.childNodes()) {
            if (node is DocumentType) {
                return node
            }
        }
        return DocumentType("xml", "", "", "")
    }

    private fun parseCommentsXml(document: Document): List<Comment> {
        val root = document.body().child(0)
        val comments = ArrayList<Comment>()
        for (i in 0 until root.childNodeSize() / 2 step 1) {
            val comment = clazz.newInstance()
            comment.fill(parseAttributesToMap(root.child(i).attributes()))
            comments.add(comment)
        }
        return comments
    }


    private fun parseAttributesToMap(attributes: Attributes): HashMap<String, String> {
        val res = HashMap<String, String>()
        for (attr in attributes) {
            res[attr.key] = attr.value
        }
        return res
    }

    class HtmlParser {

        fun parse(document: Document): List<Comment> {
            val commentNodes = getCommentNodes(document)
            for (commentNode in commentNodes) {
                val raw = HashMap<String, String>()
                parseCommentNode(commentNode, raw)
            }
            return emptyList()
        }

        private fun getCommentNodes(document: Document): Set<Element> {
            val postsNodes = document.getElementById("comment-list").getElementsByClass("post")
            val postNodesSet = HashSet<Element>()
            for (node in postsNodes) {
                if (node.attr("id").contains("p")) {
                    postNodesSet.add(node)
                }
            }
            return postNodesSet
        }

        private fun parseCommentNode(node: Element, raw: HashMap<String, String>) {
            val postId = node.attr("id").replace("p", "").toInt()
            raw["post_id"] = postId.toString()

            parsePostDataNode(node.getElementsByAttributeValue("type", "text/javascript")[0], raw)
            parsePostPreviewDataNode(node, raw)
            parseCommentDataNode(node, raw)
            println()
        }

        private fun parsePostPreviewDataNode(node: Element, raw: HashMap<String, String>) {
            val previewUrlContainer = node.getElementsByAttributeValue("alt", "thumbnail")
            raw["preview_url"] = previewUrlContainer.attr("src")
        }

        private fun parseCommentDataNode(node: Element, raw: HashMap<String, String>) {
            raw["avatar"] = node.getElementsByAttributeValue("alt", "avatar")[0].attr("src")
            val commentsData = node.getElementsByAttributeValue("type", "text/javascript")[2].dataNodes()[0]
            parseCommentData(commentsData)

            val sas1 = node.getElementsByClass("info")
            println(sas1)
        }

        private fun parseCommentData(commentsData: DataNode) {
            val wholeData = commentsData.wholeData.replace("\t", "")
            val datas = wholeData.subSequence(15, wholeData.length - 7).split("\r\n")
            val pattern = Pattern.compile("posts\\.(\\w+)\\[\\d}\\] = '(.+)'")
            for (data in datas) {
                val matcher = pattern.matcher(data)
                if (matcher.matches()) {
                    println("${matcher.group(1)} ${matcher.group(2)}")
                }
            }
        }

        private fun parsePostDataNode(node: Element, raw: HashMap<String, String>) {
            val wholeData = node.dataNodes()[0].wholeData.replace("\t", "")
            val datas = wholeData.subSequence(15, wholeData.length - 7).split("\r\n")
            val pattern = Pattern.compile("posts\\.(\\w+)\\[\\d}\\] = '(.+)'")
            for (data in datas) {
                val matcher = pattern.matcher(data)
                if (matcher.matches()) {
                    raw["post_${matcher.group(1)}"] = matcher.group(2)
                    println("${matcher.group(1)} ${matcher.group(2)}")
                }
            }
        }
    }

}
package com.makentoshe.booruchan.common.api.parser

import com.makentoshe.booruchan.common.api.entity.Comment
import org.jsoup.Jsoup
import org.jsoup.nodes.*
import java.io.InputStream
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CommentParser<T : Comment>(private val clazz: Class<T>) {

    fun parseComments(inputStream: InputStream): List<Comment> {
        val string = Scanner(inputStream).useDelimiter("\\A").next()
        return try {
            parseCommentsXml(Jsoup.parse(string))
        } catch (e: Exception) {
            throw ParseException("InputStream data is not valid xml format.", 0)
        }
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

}
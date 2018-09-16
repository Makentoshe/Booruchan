package com.makentoshe.booruchan.common.api.parser

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.makentoshe.booruchan.common.api.Posts
import com.makentoshe.booruchan.common.api.entity.Post
import org.jsoup.Jsoup
import org.jsoup.nodes.Attributes
import java.io.InputStream
import java.text.ParseException
import java.util.*
import kotlin.math.roundToInt

class PostParser<T: Post>(private val clazz: Class<T>) {

    fun parsePosts(inputStream: InputStream): Posts<T> {
        val string = Scanner(inputStream).useDelimiter("\\A").next()
        return try {
            parsePostsXml(string)
        } catch (e: Exception) {
            try {
                parsePostsJson(string)
            } catch (e: Exception) {
                e.printStackTrace()
                throw ParseException("InputStream data is not valid xml or json format.", 0)
            }
        }
    }

    private fun parsePostsXml(xml: String): Posts<T> {
        val root = Jsoup.parse(xml).body().child(0)
        val attrs = root.attributes()
        val posts = Posts<T>(attrs["count"].toInt(), attrs["offset"].toInt())
        for (i in 0 until root.childNodeSize() / 2 step 1) {
            val post = clazz.newInstance()
            post.fill(parseAttributesToMap(root.child(i).attributes()))
            posts.addPost(post)
        }
        return posts
    }

    private fun parsePostsJson(json: String): Posts<T> {
        val posts = Posts<T>(-1, 0)
        val type =  object : TypeToken<Array<T>>() {}.rawType
        val postsArray = Gson().fromJson(json, type) as Array<Any>
        for (postData in postsArray) {
            val post = clazz.newInstance()
            post.fill(correctMapValues(postData as MutableMap<String, Any>))
            posts.addPost(post)
        }
        return posts
    }

    private fun correctMapValues(map: MutableMap<String, Any>): Map<String, String> {
        for (data in map) {
            try{
                if (data.value is String) continue
                if (data.value is Double) {
                    data.setValue((data.value as Double).roundToInt().toString())
                }
                else data.setValue(data.value.toString())
            } catch (e: NullPointerException) {}
        }
        return map as Map<String, String>
    }

    private fun parseAttributesToMap(attributes: Attributes): HashMap<String, String> {
        val res = HashMap<String, String>()
        for (attr in attributes) {
            res[attr.key] = attr.value
        }
        return res
    }

}
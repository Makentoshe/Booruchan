package com.makentoshe.parser

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts

class JsonPostsParser: Parser<Posts> {

    override fun parse(data: String): Posts {
        val posts = arrayListOf<Post>()
        Gson().fromJson<JsonArray>(data, JsonArray::class.java).forEach { element ->
            val map = HashMap<String, String>()
            element.asJsonObject.entrySet().forEach {
                map[it.key] = try {
                    it.value.asString
                } catch (e: UnsupportedOperationException) {
                    ""
                }
            }
            posts.add(Post(map))
        }
        return Posts(posts)
    }
}
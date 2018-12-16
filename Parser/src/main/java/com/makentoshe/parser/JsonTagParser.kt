package com.makentoshe.parser

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.makentoshe.booruapi.Tag
import java.util.ArrayList
import java.util.HashMap

class JsonTagParser : Parser<List<Tag>> {

    override fun parse(data: String): List<Tag> {
        val list = ArrayList<Tag>()
        val any = Gson().fromJson(data, Array<Any>::class.java)
        any.forEach {
            if (it is JsonObject) list.add(parseJsonObject(it))
            if (it is JsonPrimitive) list.add(parseJsonPrimitive(it))
            if (it is String) list.add(Tag(it))
            if (it is Map<*, *>) list.add(Tag(it as Map<String, String>))
        }
        return list
    }

    private fun parseJsonObject(obj: JsonObject): Tag {
        val map = HashMap<String, String>()
        obj.entrySet().forEach {
            map[it.key] = it.value.asString
        }
        return Tag(map)
    }

    private fun parseJsonPrimitive(obj: JsonPrimitive): Tag {
        return Tag(obj.asString)
    }
}
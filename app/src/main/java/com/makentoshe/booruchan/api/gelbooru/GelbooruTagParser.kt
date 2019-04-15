package com.makentoshe.booruchan.api.gelbooru

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.api.component.parser.Parser

class GelbooruTagParser : Parser<List<Tag>> {

    override fun parse(data: String): List<Tag> {
        val list = ArrayList<Tag>()
        val any = Gson().fromJson(data, Array<Any>::class.java)
        any.forEach {
            if (it is JsonObject) list.add(parseJsonObject(it))
            if (it is JsonPrimitive) list.add(parseJsonPrimitive(it))
            if (it is String) list.add(GelbooruTag(title = it))
            if (it is Map<*, *>) list.add(GelbooruTag(it as Map<String, String>))
        }
        return list
    }

    private fun parseJsonObject(obj: JsonObject): Tag {
        val map = HashMap<String, String>()
        obj.entrySet().forEach {
            map[it.key] = it.value.asString
        }
        return GelbooruTag(map)
    }

    private fun parseJsonPrimitive(obj: JsonPrimitive): Tag {
        return GelbooruTag(title = obj.asString)
    }
}
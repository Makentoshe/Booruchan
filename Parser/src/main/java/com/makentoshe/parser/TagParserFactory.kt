package com.makentoshe.parser

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.makentoshe.booruapi.Tag
import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import java.text.ParseException
import java.util.*

class TagParserFactory : ConcreteParserFactory<Tag> {
    override fun buildParser(parserStyle: ParserStyle) = when (parserStyle) {
        ParserStyle.JSON -> JsonParser()
        ParserStyle.XML -> XmlParser()
        else -> throw ParseException("Can't parse input data.", 0)
    }

    private class XmlParser : Parser<Tag> {

        override fun parse(data: String): List<Tag> {
            val list = ArrayList<Tag>()
            Jsoup.parse(data).body().select("*").forEach {
                if (it.tag().name == "tag") {
                    list.add(Tag(convertSetToMap(it.attributes().toSet())))
                }
            }
            return list
        }

        private fun convertSetToMap(set: Set<Attribute>): Map<String, String> {
            val map = HashMap<String, String>()
            set.forEach { map[it.key] = it.value }
            return map
        }

    }

    private class JsonParser : Parser<Tag> {

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

}
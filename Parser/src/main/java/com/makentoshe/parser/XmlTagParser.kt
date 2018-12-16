package com.makentoshe.parser

import com.makentoshe.booruapi.Tag
import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import java.util.ArrayList
import java.util.HashMap

class XmlTagParser : Parser<List<Tag>> {

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
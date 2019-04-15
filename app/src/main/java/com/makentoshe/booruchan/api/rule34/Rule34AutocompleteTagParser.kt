package com.makentoshe.booruchan.api.rule34

import com.google.gson.Gson
import com.makentoshe.booruchan.api.component.parser.Parser
import com.makentoshe.booruchan.api.Tag

class Rule34AutocompleteTagParser : Parser<List<Tag>> {
    override fun parse(data: String): List<Tag> {
        val list = ArrayList<Tag>()
        val any = Gson().fromJson(data, Array<Any>::class.java)
        any.forEach { rawMapWithData ->
            rawMapWithData as Map<String, String>
            val tag = Rule34Tag(rawMapWithData)
            list.add(tag)
        }
        return list
    }
}


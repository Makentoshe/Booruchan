package com.makentoshe.booruchan.api

import org.jsoup.nodes.Attributes

fun parseAttributesToMap(attributes: Attributes): HashMap<String, String> {
    val res = HashMap<String, String>()
    for (attr in attributes) {
        res[attr.key] = attr.value
    }
    return res
}

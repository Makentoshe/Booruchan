package com.makentoshe.booruchan.boors

import com.google.gson.Gson
import com.google.gson.JsonObject

fun parseJsonForAutocomplete(json: String): ArrayList<String> {
    val results = ArrayList<String>()
    try {
        results.addAll(Gson().fromJson(json, Array<String>::class.java))
    } catch (e: Exception) {
        val map = Gson().fromJson(json, Array<JsonObject>::class.java)
        map.forEach { results.add(it.get("value").asString) }
    }
    return results
}

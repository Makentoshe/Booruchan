package com.makentoshe.booruchan.common.api.parser

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.text.ParseException

class AutocompleteSearchParser {

    fun parse(json: String): ArrayList<String> {
        return try {
            parseToStringArray(json)
        } catch (e: Exception) {
            try {
                parseToJsonObjectArray(json)
            } catch (e: Exception) {
                e.printStackTrace()
                throw ParseException("Can't parse input data.", 0)
            }
        }
    }

    private fun parseToStringArray(json: String): ArrayList<String> {
        val data = Gson().fromJson(json, Array<String>::class.java)
        val results = ArrayList<String>()
        results.addAll(data)
        return results
    }

    private fun parseToJsonObjectArray(json: String): ArrayList<String> {
        val map = Gson().fromJson(json, Array<JsonObject>::class.java)
        val results = ArrayList<String>()
        map.forEach {
            try {
                results.add(it.get("value").asString)
            } catch (e: Exception) {
                results.add(it.get("name").asString)
            }
        }
        return results
    }

}
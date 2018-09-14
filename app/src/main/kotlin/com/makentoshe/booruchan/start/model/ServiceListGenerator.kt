package com.makentoshe.booruchan.start.model

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru

class ServiceListGenerator {

    val servicesList: List<String> by lazy {
        val list = ArrayList<String>()
        for (c in classesList) {
            list.add(c.simpleName)
        }
        return@lazy list
    }

    val classesList: List<Class<Boor>> by lazy {
        return@lazy listOf(Gelbooru::class.java as Class<Boor>)
    }

    fun createAdapter(context: Context, services: List<String>): ListAdapter {
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, services)
    }

}
package com.makentoshe.booruchan.start.model

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru

class ServiceListGenerator {

    val servicesList: ArrayList<String> by lazy {
        val list = ArrayList<String>()
        for (c in classesList) {
            list.add(c.simpleName)
        }
        return@lazy list
    }
    val classesList: ArrayList<Class<Boor>> by lazy {
        val list = ArrayList<Class<Boor>>()
        list.add(Gelbooru::class.java as Class<Boor>)
        return@lazy list
    }

    fun createAdapter(context: Context, services: List<String>): ListAdapter {
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, services)
    }

}
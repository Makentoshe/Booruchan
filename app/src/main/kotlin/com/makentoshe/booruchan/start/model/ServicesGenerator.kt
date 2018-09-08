package com.makentoshe.booruchan.start.model

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.makentoshe.booruchan.boors.gelbooru.Gelbooru

class ServicesGenerator(private val context: Context) {

    private var servicesList: ArrayList<String>? = null

    fun generateList(): List<String> {
        if (servicesList == null) {
            servicesList = ArrayList()
            servicesList?.add(Gelbooru::class.java.simpleName)
        }
        return servicesList!!
    }

    fun createAdapter(services: List<String>): ListAdapter {
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, services)
    }

}
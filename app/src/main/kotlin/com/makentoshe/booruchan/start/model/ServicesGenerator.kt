package com.makentoshe.booruchan.start.model

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru

class ServicesGenerator(private val context: Context) {

    private var servicesList: ArrayList<String>? = null
    private var classesList: ArrayList<Class<Boor>>? = null

    fun generateList(): List<String> {
        if (servicesList == null) {
            servicesList = ArrayList()
            for (c in generateClassList()) {
                servicesList?.add(c.simpleName)
            }
        }
        return servicesList!!
    }

    fun generateClassList(): List<Class<Boor>> {
        if (classesList == null) {
            classesList = ArrayList()
            classesList?.add(Gelbooru::class.java as Class<Boor>)
        }
        return classesList!!
    }

    fun createAdapter(services: List<String>): ListAdapter {
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, services)
    }

}
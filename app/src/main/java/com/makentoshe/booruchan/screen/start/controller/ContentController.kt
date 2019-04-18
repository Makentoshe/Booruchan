package com.makentoshe.booruchan.screen.start.controller

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.screen.start.model.BooruContentFilter
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import org.jetbrains.anko.find

class ContentController(
    private val navigator: StartScreenNavigator,
    private val booruFactory: BooruFactory,
    private val booruContentFilter: BooruContentFilter
) {

    fun bindView(context: Context, view: View) {
        val filteredList = booruContentFilter.getBooruList()

        val listview = view.find<ListView>(R.id.start_content_listview)
        listview.adapter = buildAdapter(context, filteredList)
        listview.setOnItemClickListener { parent, view, position, id ->
            val data = ClickedItemData(parent, view, position, id, context)
            onItemClick(filteredList, data)
        }
    }

    private fun buildAdapter(
        context: Context,
        booruList: List<Class<out Booru>>
    ): ListAdapter {
        val boorusTitles = Array(booruList.size) { booruList[it].simpleName }
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, boorusTitles)
    }

    private fun onItemClick(booruList: List<Class<out Booru>>, data: ClickedItemData) {
        val booru = booruFactory.buildBooru(booruList[data.position], data.context)
        navigator.navigateToBooruScreen(booru)
    }

    private data class ClickedItemData(
        val parent: AdapterView<*>,
        val view: View,
        val position: Int,
        val id: Long,
        val context: Context
    )
}
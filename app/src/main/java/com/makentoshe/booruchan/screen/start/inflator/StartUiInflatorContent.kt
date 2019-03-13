package com.makentoshe.booruchan.screen.start.inflator

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.core.util.Consumer
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.network.fuel.FuelClientFactory
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import org.jetbrains.anko.find

class StartUiInflatorContent(
    private val navigator: StartScreenNavigator,
    private val booruList: List<Class<out Booru>>
) : Consumer<View> {
    override fun accept(view: View) {
        val view = view.find<ListView>(R.id.start_content_listview)
        view.adapter = buildAdapter(view.context)
        view.setOnItemClickListener(::onItemClick)
    }

    private fun buildAdapter(context: Context): ListAdapter {
        val boorusTitles = Array(booruList.size) { booruList[it].simpleName }
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, boorusTitles)
    }

    private fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val httpClient = FuelClientFactory().buildClient()
        val booru = BooruFactory(httpClient).buildBooru(booruList[position], parent.context)
        navigator.navigateToBooruScreen(booru, setOf())
    }
}
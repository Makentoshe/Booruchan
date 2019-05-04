package com.makentoshe.booruchan.screen.start.controller

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import org.jetbrains.anko.find
import org.koin.core.KoinComponent
import org.koin.core.inject

class ContentController(
    private val navigator: StartScreenNavigator,
    private val booruFactory: BooruFactory,
    private val booruList: List<Class<out Booru>>
) : KoinComponent {

    private val appSettings by inject<AppSettings>()

    fun bindView(context: Context, view: View) {
        val filteredList = booruList.map {
            booruFactory.buildBooru(it, context)
        }.filter {
            appSettings.getNsfw(context) or it.nsfw.not()
        }

        val listview = view.find<ListView>(R.id.start_content_listview)
        listview.adapter = buildAdapter(context, filteredList)
        listview.setOnItemClickListener { _, _, position, _ ->
            onItemClick(filteredList, position)
        }
    }

    private fun buildAdapter(context: Context, booruList: List<Booru>): ListAdapter {
        val boorusTitles = Array(booruList.size) { booruList[it].title }
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, boorusTitles)
    }

    private fun onItemClick(booruList: List<Booru>, position: Int) {
        navigator.navigateToBooruScreen(booruList[position])
    }
}
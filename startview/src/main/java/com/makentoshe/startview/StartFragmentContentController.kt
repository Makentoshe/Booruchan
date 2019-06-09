package com.makentoshe.startview

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.makentoshe.api.BooruRepository
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.settings.model.SettingController
import com.makentoshe.settings.screen.controller.NsfwSettingController
import java.io.Serializable

/**
 * [ListView] content controller performs displaying a list of available booru.
 *
 * @param repository can return a list of all booru.
 * @param settingController checks nsfw setting.
 * @param navigator performs navigation to another screen
 */
class StartFragmentContentController(
    private val repository: BooruRepository,
    private val settingController: SettingController<Boolean>,
    private val navigator: StartFragmentNavigator
) : Serializable {

    /** [ListView] item layout id */
    private val elementLayout = android.R.layout.simple_list_item_1

    /** Property for returning a list of booru satisfies a nsfw setting */
    private val booruList: List<Booru>
        get() {
            val nsfwSetting = NsfwSettingController(settingController).get()
            return repository.get(Unit).filter { nsfwSetting or it.isNsfw }
        }

    /** Binds controller to the [view] */
    fun bindView(view: ListView) {
        view.adapter = buildAdapter(view.context)
        view.setOnItemClickListener(::onItemClick)
    }

    private fun onItemClick(adapter: AdapterView<*>, view: View, position: Int, id: Long) {
        val booru = booruList[position]
        navigator.navigateToBooruScreen(booru)
    }

    /** Build an adapter for displaying a booru */
    private fun buildAdapter(context: Context): ArrayAdapter<String> {
        return ArrayAdapter(context, elementLayout, booruList.map { it.title })
    }

}
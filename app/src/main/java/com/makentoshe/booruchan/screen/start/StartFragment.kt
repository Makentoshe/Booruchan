package com.makentoshe.booruchan.screen.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.api.BooruFactoryImpl
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.network.fuel.FuelClientFactory
import com.makentoshe.booruchan.router
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import com.makentoshe.booruchan.screen.start.view.StartFragmentUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class StartFragment : Fragment() {

    private val navigator by lazy {
        StartScreenNavigator(router)
    }

    var booruFactory: BooruFactory = with(FuelClientFactory().buildClient()) {
        BooruFactoryImpl(this)
    }

    private val booruList: List<Class<out Booru>>
        get() = if (AppSettings.getNsfw(requireContext())) {
            Booruchan.INSTANCE.booruList
        } else {
            Booruchan.INSTANCE.booruList.filter { it == Safebooru::class.java }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val icon = view.find<View>(R.id.start_toolbar_overflow)
        icon.setOnClickListener(::onOverflowIconClick)

        buildBooruListView(view)
    }

    private fun buildBooruListView(view: View) {
        val listview = view.find<ListView>(R.id.start_content_listview)
        listview.adapter = buildAdapter(requireContext())
        listview.setOnItemClickListener(::onItemClick)
    }

    private fun buildAdapter(context: Context): ListAdapter {
        val boorusTitles = Array(booruList.size) { booruList[it].simpleName }
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, boorusTitles)
    }

    private fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val booru = booruFactory.buildBooru(booruList[position], requireContext())
        navigator.navigateToBooruScreen(booru)
    }

    private fun onOverflowIconClick(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menu.add(Menu.NONE, R.id.settings, 1, view.context.getString(R.string.settings))
        popupMenu.setOnMenuItemClickListener(::onOverflowItemMenuClick)
        popupMenu.show()
    }

    private fun onOverflowItemMenuClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                navigator.navigateToSettingsScreen()
                return true
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCode.settings) {
            //update list view
            buildBooruListView(view!!)
        }
    }

    override fun onResume() {
        super.onResume()
        println("Resume")
    }
}
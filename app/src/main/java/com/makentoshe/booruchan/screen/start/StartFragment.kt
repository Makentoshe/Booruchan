package com.makentoshe.booruchan.screen.start

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.api.BooruFactoryImpl
import com.makentoshe.booruchan.network.fuel.FuelClientFactory
import com.makentoshe.booruchan.router
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import com.makentoshe.booruchan.screen.start.view.StartFragmentUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class StartFragment : Fragment() {

    private val navigator by lazy {
        StartScreenNavigator(router)
    }

    private val booruList by lazy {
        Booruchan.INSTANCE.booruList
    }

    var booruFactory: BooruFactory = with(FuelClientFactory().buildClient()) {
        BooruFactoryImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val icon = view.find<View>(R.id.start_toolbar_overflow)
        icon.setOnClickListener(::onOverflowIconClick)

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
}
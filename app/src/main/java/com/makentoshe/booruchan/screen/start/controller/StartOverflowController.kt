package com.makentoshe.booruchan.screen.start.controller

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import org.jetbrains.anko.find
import org.koin.core.KoinComponent
import org.koin.core.inject

class StartOverflowController : KoinComponent {

    private val navigator by inject<StartScreenNavigator>()

    fun bindView(context: Context, view: View) {
        val overflowIcon = view.find<View>(R.id.start_toolbar_overflow)
        overflowIcon.setOnClickListener {
            createPopup(context, it)
        }
    }

    private fun createPopup(context: Context, anchor: View) {
        val popupMenu = PopupMenu(context, anchor)
        popupMenu.menu.add(Menu.NONE, R.id.settings, 1, context.getString(R.string.settings))
        popupMenu.setOnMenuItemClickListener(::onClick)
        popupMenu.show()
    }

    private fun onClick(item: MenuItem): Boolean = when (item.itemId) {
        R.id.settings -> true.also { navigator.navigateToSettingsScreen() }
        else -> false
    }
}
package com.makentoshe.booruchan.screen.start.inflator

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.util.Consumer
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import org.jetbrains.anko.find

class StartUiInflatorOverflow(private val navigator: StartScreenNavigator) : Consumer<View> {
    override fun accept(view: View) {
        val icon = view.find<View>(R.id.start_toolbar_overflow)
        icon.setOnClickListener(::onClick)
    }

    private fun onClick(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menu.add(Menu.NONE, R.id.settings, 1, view.context.getString(R.string.settings))
        popupMenu.setOnMenuItemClickListener(::onItemMenuClick)
        popupMenu.show()
    }

    private fun onItemMenuClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                navigator.navigateToSettingsScreen()
                return true
            }
        }
        return false
    }
}
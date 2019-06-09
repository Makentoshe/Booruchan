package com.makentoshe.startview

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import java.io.Serializable

/**
 * Overflow icon controller performs displaying a popup menu.
 *
 * @param navigator performs navigation to another screen
 */
class StartFragmentOverflowController(private val navigator: StartFragmentNavigator) : Serializable {

    /** Binds controller to the [view] */
    fun bindView(view: View) = view.setOnClickListener {
        val context = view.context
        val popupMenu = PopupMenu(context, view)
        buildSettingsMenuItem(popupMenu.menu, context)
        popupMenu.setOnMenuItemClickListener(::onClick)
        popupMenu.show()
    }

    private fun buildSettingsMenuItem(menu: Menu, context: Context) {
        val id = com.makentoshe.startview.R.id.settings
        val title = context.getString(R.string.settings)
        menu.add(Menu.NONE, id, 1, title)
    }

    private fun onClick(item: MenuItem): Boolean = when (item.itemId) {
        com.makentoshe.startview.R.id.settings -> true.also { navigator.navigateToSettingsScreen() }
        else -> false
    }
}
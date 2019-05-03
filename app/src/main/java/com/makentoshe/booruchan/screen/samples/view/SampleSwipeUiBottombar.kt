package com.makentoshe.booruchan.screen.samples.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.Menu
import android.view.MenuItem
import android.view.ViewManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.style.getColorFromStyle
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SampleSwipeUiBottombar : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        bottomNavigationView(style.toolbar) {
            id = R.id.samples_bottombar

            menu.buildMenuItem(
                context, R.string.info,
                R.id.bottombar_infoitem,
                R.drawable.ic_info
            )
            menu.buildMenuItem(
                context, R.string.tags,
                R.id.bottombar_tagsitem,
                R.drawable.ic_tag
            )
            menu.buildMenuItem(
                context, R.string.comments,
                R.id.bottombar_commentsitem,
                R.drawable.ic_comments
            )

            setMenuItemColor(
                getColorFromStyle(android.R.attr.textColor),
                getColorFromStyle(android.R.attr.colorAccent)
            )

        }.lparams(matchParent, wrapContent) {
            alignParentBottom()
        }
    }

    private fun Menu.buildMenuItem(context: Context, title: Int, id: Int, icon: Int) =
        add(Menu.NONE, id, Menu.NONE, title).apply {
            isEnabled = true
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            val iconColor = context.getColorFromStyle(android.R.attr.textColor)
            setIcon(icon).icon.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP)
        }

    private fun BottomNavigationView.setMenuItemColor(textColor: Int, textColorAlt: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled))
        val colors = intArrayOf(textColor, textColorAlt)
        itemTextColor = ColorStateList(states, colors)
        itemIconTintList = itemTextColor
    }

    private fun ViewManager.bottomNavigationView(
        theme: Int = 0,
        init: BottomNavigationView.() -> Unit
    ): BottomNavigationView {
        return ankoView({ BottomNavigationView(it) }, theme, init)
    }
}
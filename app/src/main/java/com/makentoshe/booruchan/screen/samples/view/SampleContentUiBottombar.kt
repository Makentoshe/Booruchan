package com.makentoshe.booruchan.screen.samples.view

import android.content.res.ColorStateList
import android.view.Menu
import android.view.MenuItem
import android.view.ViewManager
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.style.getColorFromStyle
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SampleContentUiBottombar : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        bottomNavigationView(style.toolbar) {
            id = R.id.samples_bottombar
            menu.buildMenuItem(
                R.string.info,
                R.id.bottombar_infoitem
            )
            menu.buildMenuItem(
                R.string.tags,
                R.id.bottombar_tagsitem
            )
//            menu.buildMenuItem(
//                R.string.comments,
//                R.id.bottombar_commentsitem
//            )

            setTextColor(
                getColorFromStyle(android.R.attr.textColor),
                getColorFromStyle(android.R.attr.colorAccent)
            )
        }.lparams(matchParent, wrapContent) {
            alignParentBottom()
        }
    }

    private fun Menu.buildMenuItem(@StringRes title: Int, @IdRes id: Int) = add(
        Menu.NONE, id,
        Menu.NONE, title
    ).apply {
        isEnabled = true
        setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }

    private fun BottomNavigationView.setTextColor(textColor: Int, textColorAlt: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled))
        val colors = intArrayOf(textColor, textColorAlt)
        itemTextColor = ColorStateList(states, colors)
    }

    private fun ViewManager.bottomNavigationView(
        theme: Int = 0,
        init: BottomNavigationView.() -> Unit
    ): BottomNavigationView {
        return ankoView({ BottomNavigationView(it) }, theme, init)
    }
}
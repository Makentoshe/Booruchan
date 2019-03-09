package com.makentoshe.booruchan.screen.samples.view

import android.content.res.ColorStateList
import android.view.Menu
import android.view.MenuItem
import android.view.ViewManager
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SampleContentUiBottombar : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        bottomNavigationView {
            id = R.id.samples_bottombar
            backgroundColorResource = style.toolbar.primaryColorRes
            menu.buildMenuItem(
                R.string.info,
                R.id.postsamples_bottombar_infoitem
            )
            menu.buildMenuItem(
                R.string.tags,
                R.id.postsamples_bottombar_tagsitem
            )
            menu.buildMenuItem(
                R.string.comments,
                R.id.postsamples_bottombar_commentsitem
            )
            setTextColor(style.toolbar.getOnPrimaryColor(context), style.toolbar.getSecondaryColor(context))
        }.lparams {
            alignParentBottom()
        }
    }

    private fun Menu.buildMenuItem(@StringRes title: Int, @IdRes id: Int) = add(
        Menu.NONE, id,
        Menu.NONE, title).apply {
        isEnabled = true
        setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }

    private fun BottomNavigationView.setTextColor(textColor: Int, textColorAlt: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled))
        val colors = intArrayOf(textColor, textColorAlt)
        itemTextColor = ColorStateList(states, colors)
    }

    private fun ViewManager.bottomNavigationView(init: BottomNavigationView.() -> Unit): BottomNavigationView {
        return ankoView({ BottomNavigationView(it) }, 0, init)
    }
}
package com.makentoshe.booruchan.postsamples.view

import android.content.res.ColorStateList
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewManager
import androidx.annotation.StringRes
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makentoshe.booruchan.R
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class PostSamplesUiBottombar(
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        bottomNavigationView {
            id = R.id.postsamples_bottombar
            backgroundColorResource = style.toolbar.primaryColorRes
            buildMenu()
            setTextColor(style.toolbar.getOnPrimaryColor(context), style.toolbar.getSecondaryColor(context))
            setOnNavigationItemSelectedListener { onItemSelected(it) }
        }.lparams(matchParent, dip(56)) {
            alignParentBottom()
        }
    }

    private fun BottomNavigationView.onItemSelected(menuItem: MenuItem): Boolean {
        for (i in 0 until menu.size()) {
            val item = menu[i]
            item.isEnabled = item != menuItem
        }
        return true
    }

    private fun BottomNavigationView.buildMenu() = menu.apply {
        buildMenuItem(R.string.info)
        buildMenuItem(R.string.image).apply { isEnabled = false }
        buildMenuItem(R.string.comments)
    }

    private fun Menu.buildMenuItem(@StringRes title: Int) = add(title).apply {
        isEnabled = true
        setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }

    private fun ViewManager.bottomNavigationView(init: BottomNavigationView.() -> Unit): BottomNavigationView {
        return ankoView({ BottomNavigationView(it) }, 0, init)
    }

    private fun BottomNavigationView.setTextColor(textColor: Int, textColorAlt: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled))
        val colors = intArrayOf(textColor, textColorAlt)
        itemTextColor = ColorStateList(states, colors)
    }
}
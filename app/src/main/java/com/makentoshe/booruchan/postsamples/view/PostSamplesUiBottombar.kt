package com.makentoshe.booruchan.postsamples.view

import android.content.res.ColorStateList
import android.view.MenuItem
import android.view.View
import android.view.ViewManager
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
            setTextColor(style.toolbar.getOnPrimaryColor(context))
            setOnNavigationItemSelectedListener { onItemSelected(it) }
        }.lparams(matchParent, dip(56)) {
            alignParentBottom()
        }
    }

    private fun BottomNavigationView.onItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

    private fun BottomNavigationView.buildMenu() {
        menu.add(R.string.posts).apply {
            isEnabled = true
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        menu.add(R.string.info).apply {
            isEnabled = true
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        menu.add(R.string.comments).apply {
            isEnabled = true
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
    }

    private fun ViewManager.bottomNavigationView(init: BottomNavigationView.() -> Unit): BottomNavigationView {
        return ankoView({ BottomNavigationView(it) }, 0, init)
    }

    private fun BottomNavigationView.setTextColor(color: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
        val colors = intArrayOf(color)
        itemTextColor = ColorStateList(states, colors)
    }
}
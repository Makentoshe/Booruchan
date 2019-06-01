package com.makentoshe.booruview

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4._DrawerLayout
import org.jetbrains.anko.support.v4.drawerLayout

class BooruFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        drawerLayout {
            id = R.id.drawer
            createBackView()
            createPanelView()

            lparams(matchParent, matchParent)
        }
    }

    private fun _DrawerLayout.createPanelView() = frameLayout {
        id = R.id.panel
    }.lparams(matchParent, matchParent) {
        gravity = Gravity.START
    }

    private fun _DrawerLayout.createBackView() = frameLayout {
        id = R.id.content
    }.lparams(matchParent, matchParent)
}
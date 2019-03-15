package com.makentoshe.booruchan.screen.booru.inflator

import android.view.View
import androidx.core.util.Consumer
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.makentoshe.booruchan.R

open class BooruToolbarUiInflater(private val drawer: DrawerLayout) :
    Consumer<View> {
    override fun accept(view: View) {
        val icon = view.findViewById<View>(R.id.booru_toolbar_drawermenu)
        icon.setOnClickListener { onClick(drawer) }
    }

    private fun onClick(view: DrawerLayout) {
        if (view.isDrawerOpen(GravityCompat.START)) {
            view.closeDrawer(GravityCompat.START)
        } else {
            view.openDrawer(GravityCompat.START)
        }
    }
}
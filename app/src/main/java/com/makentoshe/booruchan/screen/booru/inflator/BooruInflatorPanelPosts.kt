package com.makentoshe.booruchan.screen.booru.inflator

import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import com.makentoshe.booruchan.screen.booru.model.LocalNavigator
import org.jetbrains.anko.find

class BooruInflatorPanelPosts(private val navigator: LocalNavigator) :
    Inflator {

    override fun inflate(view: View) {
        val drawer = view.find<DrawerLayout>(R.id.booru_drawer)
        val view = view.find<View>(R.id.booru_drawer_panel_posts)
        view.setOnClickListener { onClick(drawer) }
    }

    private fun onClick(view: DrawerLayout) {
        navigator.navigateToPosts()
        view.closeDrawer(GravityCompat.START)
    }
}
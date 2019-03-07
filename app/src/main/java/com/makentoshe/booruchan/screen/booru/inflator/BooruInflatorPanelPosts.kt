package com.makentoshe.booruchan.screen.booru.inflator

import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import com.makentoshe.booruchan.screen.booru.model.LocalNavigator
import org.jetbrains.anko.find

class BooruInflatorPanelPosts(private val navigator: LocalNavigator) :
    Inflator {

    override fun inflate(view: View) {
        val view = view.find<View>(R.id.booru_drawer_panel_posts)
        view.setOnClickListener(::onClick)
    }

    private fun onClick(view: View) = navigator.navigateToPosts()
}
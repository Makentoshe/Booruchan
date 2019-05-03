package com.makentoshe.booruchan.screen.posts.container.controller

import android.view.View
import android.widget.Toolbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import org.jetbrains.anko.find
import org.jetbrains.anko.subtitleResource

class ToolbarController(private val booru: Booru) {

    fun bindView(view: View) {
        val toolbar = view.find<Toolbar>(R.id.booru_toolbar)
        toolbar.title = booru.title
        toolbar.subtitleResource = R.string.posts
    }
}
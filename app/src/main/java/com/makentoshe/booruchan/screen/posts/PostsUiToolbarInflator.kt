package com.makentoshe.booruchan.screen.posts

import android.view.View
import android.widget.Toolbar
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import org.jetbrains.anko.find
import org.jetbrains.anko.subtitleResource

class PostsUiToolbarInflator(private val booru: Booru):
    Inflator {
    override fun inflate(view: View) {
        val view = view.find<Toolbar>(R.id.booru_toolbar)
        view.title = booru.title
        view.subtitleResource = R.string.posts
    }
}
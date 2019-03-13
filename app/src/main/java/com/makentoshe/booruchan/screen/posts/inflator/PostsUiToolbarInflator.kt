package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import android.widget.Toolbar
import androidx.core.util.Consumer
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import org.jetbrains.anko.find
import org.jetbrains.anko.subtitleResource

class PostsUiToolbarInflator(private val booru: Booru) : Consumer<View> {
    override fun accept(view: View) {
        val view = view.find<Toolbar>(R.id.booru_toolbar)
        view.title = booru.title
        view.subtitleResource = R.string.posts
    }
}
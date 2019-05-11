package com.makentoshe.booruchan.screen.posts.container.view

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import org.jetbrains.anko.*

class PostsUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            PostsUiToolbar().createView(AnkoContext.createDelegate(this))
            PostsUiContent().createView(AnkoContext.createDelegate(this))
            PostsUiBottombar().createView(AnkoContext.createDelegate(this))
        }
    }
}
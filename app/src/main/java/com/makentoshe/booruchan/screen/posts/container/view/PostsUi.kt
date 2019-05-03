package com.makentoshe.booruchan.screen.posts.container.view

import android.view.View
import androidx.fragment.app.Fragment
import org.jetbrains.anko.*

class PostsUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            PostsUiToolbar().createView(AnkoContext.createDelegate(this))
            PostsUiContent().createView(AnkoContext.createDelegate(this))
            PostsUiBottombar().createView(AnkoContext.createDelegate(this))
        }
    }
}
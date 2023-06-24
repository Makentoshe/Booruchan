package com.makentoshe.booruchan.screen.posts.view

import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class PostsUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        viewPager {
            id = R.id.posts_viewpager
        }.lparams {
            centerInParent()
            //wtf? - the below and above rules does not works
            setMargins(0, dip(56), 0, dip(56))
        }
    }
}
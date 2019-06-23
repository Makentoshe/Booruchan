package com.makentoshe.boorupostview.view

import android.content.Context
import android.graphics.Color
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.viewPager

class PostsViewPagerFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        viewPager {
            id = com.makentoshe.boorupostview.R.id.viewpager
        }
    }
}
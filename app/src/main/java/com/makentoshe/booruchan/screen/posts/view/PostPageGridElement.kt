package com.makentoshe.booruchan.screen.posts.view

import android.content.Context
import android.view.View
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import kotlin.random.Random

class PostPageGridElement : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        cardView {
            radius = 0f
            elevation = dip(4).toFloat()
            imageView {
                setPadding(dip(4), dip(4), dip(4), dip(4))
                id = R.id.posts_page_gridview_element_image
            }.lparams(matchParent, matchParent)
            lparams(dip(100), dip(100))
        }
    }
}
package com.makentoshe.booruchan.screen.posts.view

import android.content.Context
import android.graphics.Color
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class PostPageGridElementUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        cardView {
            radius = 0f
            elevation = dip(4).toFloat()
            lparams(dip(100), dip(100))

            relativeLayout {

                imageView {
                    id = R.id.posts_page_gridview_element_image
                    setPadding(dip(4), dip(4), dip(4), dip(4))
                }.lparams(matchParent, matchParent)

                themedImageView(style.default) {
                    alpha = 0.75f
                    id = R.id.posts_page_gridview_element_type
                    backgroundColor = Color.WHITE
                }.lparams(height = dip(12)) {
                    alignParentRight()
                    alignParentBottom()
                }

            }.lparams(matchParent, matchParent)
        }
    }
}
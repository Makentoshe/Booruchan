package com.makentoshe.booruchan.screen.posts.view

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class PostPageUiGrid : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        gridView {
            id = R.id.posts_page_gridview
            visibility = View.GONE
            gravity = Gravity.CENTER

            context.configuration(orientation = Orientation.PORTRAIT) {
                topPadding = dip(12)
                numColumns = 3
                verticalSpacing = dip(10)
            }
            context.configuration(orientation = Orientation.LANDSCAPE) {
                numColumns = 6
                verticalSpacing = dip(10)
            }
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
    }
}
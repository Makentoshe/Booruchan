package com.makentoshe.booruchan.screen.posts.view

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.model.getItemsCountInColumnF
import com.makentoshe.booruchan.screen.posts.model.getItemsCountInRowF
import org.jetbrains.anko.*

class PostPageUiGrid : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        val verticalSpace =
            (getItemsCountInColumnF(context) - getItemsCountInColumnF(context).toInt()) /
                    getItemsCountInRowF(context).toInt() * 100

        gridView {
            id = R.id.posts_page_gridview
            visibility = View.GONE
            gravity = Gravity.CENTER
            verticalSpacing = dip(verticalSpace)
            numColumns = getItemsCountInRowF(context).toInt()
            context.configuration(orientation = Orientation.PORTRAIT) {
                topPadding = dip(verticalSpace / 2)
            }
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
    }
}
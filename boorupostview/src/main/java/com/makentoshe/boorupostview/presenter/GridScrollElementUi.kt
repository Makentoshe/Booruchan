package com.makentoshe.boorupostview.presenter

import android.content.Context
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7._CardView
import org.jetbrains.anko.cardview.v7.cardView

/**
 * A grid element ui.
 */
class GridScrollElementUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        cardView {
            radius = 0f
            elevation = dip(4).toFloat()
            lparams(dip(100), dip(100))
            createContent()
        }
    }

    private fun _CardView.createContent() = relativeLayout {
        imageView {
            id = com.makentoshe.boorupostview.R.id.gridview_element_image
            setPadding(dip(4), dip(4), dip(4), dip(4))
        }.lparams(matchParent, matchParent)
    }.lparams(matchParent, matchParent)
}
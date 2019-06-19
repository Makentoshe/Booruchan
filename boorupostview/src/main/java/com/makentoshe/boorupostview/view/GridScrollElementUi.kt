package com.makentoshe.boorupostview.view

import android.content.Context
import android.graphics.Color
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * A grid element ui.
 */
class GridScrollElementUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        val dip4 = dip(4)
        val dip8 = dip(8)
        val dip100 = dip(100)

        cardView {
            radius = 0f
            elevation = dip4.toFloat()
            lparams(dip100, dip100)

            relativeLayout {

                imageView {
                    id = com.makentoshe.boorupostview.R.id.gridview_element_image
                    setPadding(dip4, dip4, dip4, dip4)
                }.lparams(matchParent, matchParent)

                horizontalProgressBar {
                    id = com.makentoshe.boorupostview.R.id.gridview_element_progress
                    max = 100
                    backgroundColor = Color.TRANSPARENT
                }.lparams(width = matchParent) {
                    alignParentBottom()
                    setMargins(dip8, dip8, dip8, 0)
                }

            }.lparams(matchParent)

        }
    }
}
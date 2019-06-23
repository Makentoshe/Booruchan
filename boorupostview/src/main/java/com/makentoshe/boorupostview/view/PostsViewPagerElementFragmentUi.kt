package com.makentoshe.boorupostview.view

import android.content.Context
import android.view.Gravity
import android.view.View
import com.makentoshe.boorupostview.model.ItemsCountCalculator
import org.jetbrains.anko.*

/**
 * @param calculator performs elements count and spacing calculations for a grid view.
 */
class PostsViewPagerElementFragmentUi(private val calculator: ItemsCountCalculator) : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        relativeLayout {
            createGridView()
            createProgressBar()
            createMessageView()
        }
    }

    /** Indeterminate progress bar in the center of the layout */
    private fun _RelativeLayout.createProgressBar() = progressBar {
        id = com.makentoshe.boorupostview.R.id.progressbar
        isIndeterminate = true
    }.lparams {
        centerInParent()
    }

    /** Grid view with a posts preview images and calculated elements counts and spacing */
    private fun _RelativeLayout.createGridView() = gridView {
        val row = calculator.getItemsCountInRow(context)
        val column = calculator.getItemsCountInColumn(context)

        val verticalSpace = (column - column.toInt()) / row.toInt() * 100

        id = com.makentoshe.boorupostview.R.id.gridview
        visibility = View.GONE
        gravity = Gravity.CENTER
        verticalSpacing = dip(verticalSpace)
        numColumns = row.toInt()
        context.configuration(orientation = Orientation.PORTRAIT) {
            topPadding = dip(verticalSpace / 2)
        }
    }.lparams(matchParent, matchParent)

    /** Message view in the center of the layout*/
    private fun _RelativeLayout.createMessageView() = textView {
        id = com.makentoshe.boorupostview.R.id.messageview
        setPadding(dip(8), 0, dip(8), 0)
        gravity = Gravity.CENTER
        visibility = View.GONE
    }.lparams {
        centerInParent()
        gravity = Gravity.CENTER
    }
}
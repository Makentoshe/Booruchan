package com.makentoshe.boorusamplesview.view

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import com.makentoshe.boorusamplesview.R
import com.makentoshe.style.circularProgressBar
import com.makentoshe.style.subsamplingScaleImageView
import org.jetbrains.anko.*

class ImageViewPagerElementFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = ui.relativeLayout {
        // composite progress bar
        ImageViewPagerElementFragmentUiProgressBar().createView(AnkoContext.createDelegate(this))
        // for displaying a default sample-size images
        subsamplingScaleImageView {
            id = R.id.imageview
            isZoomEnabled = true
            minScale = 2f
            maxScale = 2f
            visibility = View.GONE
        }.lparams(matchParent, matchParent)

    }

}

/**
 * User interface class for composite progress bar.
 * Contains 2 view elements - a default indeterminate circular progress bar
 * and a circular progress bar, wraps the indeterminate progress bar, with the progress displaying.
 */
class ImageViewPagerElementFragmentUiProgressBar : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        val width = 6 * resources.displayMetrics.density
        circularProgressBar {
            id = R.id.circularprogress
            setProgressWidth(width.toInt())
        }.lparams(dip(56), dip(56)) {
            centerInParent()
        }

        progressBar {
            id = R.id.indeterminateprogress
            backgroundColor = Color.TRANSPARENT
        }.lparams(dip(56) - (width * 2).toInt(), dip(56) - (width * 2).toInt()) {
            gravity = Gravity.CENTER
            alignStart(R.id.circularprogress)
            alignEnd(R.id.circularprogress)
            sameTop(R.id.circularprogress)
            sameBottom(R.id.circularprogress)
        }

    }
}
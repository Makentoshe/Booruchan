package com.makentoshe.boorusamplesview.view

import android.graphics.Color
import android.view.Gravity
import com.makentoshe.boorusamplesview.R
import com.makentoshe.style.circularProgressBar
import org.jetbrains.anko.*

/**
 * User interface class for composite progress bar.
 * Contains 2 view elements - a default indeterminate circular progress bar
 * and a circular progress bar, wraps the indeterminate progress bar, with the progress displaying.
 */
class CompositeProgressBar : AnkoComponent<_RelativeLayout> {

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
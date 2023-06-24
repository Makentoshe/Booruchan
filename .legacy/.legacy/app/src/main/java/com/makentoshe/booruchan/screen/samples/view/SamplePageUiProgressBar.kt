package com.makentoshe.booruchan.screen.samples.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.style.getColorFromStyle
import com.makentoshe.booruchan.view.CircularProgressBar
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SamplePageUiProgressBar : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        relativeLayout {
            id = R.id.samples_progress

            imageView {
                setImageResource(R.drawable.ic_circle_closed)
                imageAlpha = 200
            }.lparams(dip(64), dip(64)) { centerInParent() }

            themedProgressBar(style.progress) {
                isIndeterminate = true
                indeterminateDrawable.setColorFilter(
                    getColorFromStyle(android.R.attr.indeterminateDrawable),
                    PorterDuff.Mode.SRC_ATOP
                )
            }.lparams { centerInParent() }

            circularProgressBar {
                id = R.id.samples_progress_concrete
                setProgressColor(getColorFromStyle(R.attr.colorPrimary))
                setTextColor(Color.TRANSPARENT)
                setProgressWidth(10)
            }.lparams(dip(52), dip(52)) { centerInParent() }



        }.lparams { centerInParent() }
    }

    private fun ViewManager.circularProgressBar(init: CircularProgressBar.() -> Unit): CircularProgressBar {
        return ankoView({ CircularProgressBar(it) }, style.progress, init)
    }
}
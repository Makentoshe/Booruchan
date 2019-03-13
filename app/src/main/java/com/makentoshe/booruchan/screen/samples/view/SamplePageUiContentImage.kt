package com.makentoshe.booruchan.screen.samples.view

import android.view.View
import android.view.ViewManager
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SamplePageUiContentImage : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        subsamplingScaleImageView{
            id = R.id.samples_image
            visibility = View.GONE
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
    }

    private fun ViewManager.subsamplingScaleImageView(init: SubsamplingScaleImageView.() -> Unit): SubsamplingScaleImageView {
        return ankoView({ SubsamplingScaleImageView(it) }, 0, init)
    }
}
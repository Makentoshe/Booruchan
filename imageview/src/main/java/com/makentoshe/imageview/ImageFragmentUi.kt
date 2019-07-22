package com.makentoshe.imageview

import android.content.Context
import android.view.View
import com.makentoshe.style.CompositeProgressBar
import com.makentoshe.style.subsamplingScaleImageView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

class ImageFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = ui.relativeLayout {
        // progress bar
        CompositeProgressBar().createView(AnkoContext.createDelegate(this))
        // content
        subsamplingScaleImageView {
            id = R.id.imageview
            isZoomEnabled = true
            minScale = 2f
            maxScale = 2f
            visibility = View.GONE
        }.lparams(matchParent, matchParent)
    }
}
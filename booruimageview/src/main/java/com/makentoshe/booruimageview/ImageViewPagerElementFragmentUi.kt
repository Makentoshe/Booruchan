package com.makentoshe.booruimageview

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import com.makentoshe.style.circularProgressBar
import com.makentoshe.style.subsamplingScaleImageView
import org.jetbrains.anko.*

class ImageViewPagerElementFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = ui.relativeLayout {
        subsamplingScaleImageView {
            id = com.makentoshe.booruimageview.R.id.imageview
            isZoomEnabled = true
            minScale = 2f
            maxScale = 2f
        }.lparams(matchParent, matchParent)

        val width = 6 * resources.displayMetrics.density
        circularProgressBar {
            id = com.makentoshe.booruimageview.R.id.circularprogress
            setProgressWidth(width.toInt())
        }.lparams(dip(56), dip(56)) {
            centerInParent()
        }

        progressBar {
            id = com.makentoshe.booruimageview.R.id.indeterminateprogress
            backgroundColor = Color.TRANSPARENT
        }.lparams(dip(56) - (width * 2).toInt(), dip(56) - (width * 2).toInt()) {
            gravity = Gravity.CENTER
            alignStart(com.makentoshe.booruimageview.R.id.circularprogress)
            alignEnd(com.makentoshe.booruimageview.R.id.circularprogress)
            sameTop(com.makentoshe.booruimageview.R.id.circularprogress)
            sameBottom(com.makentoshe.booruimageview.R.id.circularprogress)
        }

    }
}
package com.makentoshe.boorupostview

import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MagnifyPanelSlideListener(private val magnifyView: View) : SlidingUpPanelLayout.SimplePanelSlideListener() {
    /* Hide the magnifyView when panel is fully hide (offset == 0) */
    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        magnifyView.alpha = slideOffset
        magnifyView.visibility = if (slideOffset == 0f) View.GONE else View.VISIBLE
    }
}
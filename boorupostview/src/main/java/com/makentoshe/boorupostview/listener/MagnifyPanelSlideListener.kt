package com.makentoshe.boorupostview.listener

import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class MagnifyPanelSlideListener(
    private val subject: Subject<Float>
) : SlidingUpPanelLayout.SimplePanelSlideListener() {
    /* Hide the magnifyView when panel is fully hide (offset == 0) */
    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        subject.onNext(slideOffset)
    }
}
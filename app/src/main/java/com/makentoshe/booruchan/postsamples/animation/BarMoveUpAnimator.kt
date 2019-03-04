package com.makentoshe.booruchan.postsamples.animation

import android.view.View
import com.makentoshe.booruchan.postpreviews.animations.ViewAnimator

class BarMoveUpAnimator(
    target: View,
    private val duration: Long
) : ViewAnimator<View>(target) {
    override fun animate() = with(target) {
        animate().setDuration(duration).y(y - height).start()
    }
}
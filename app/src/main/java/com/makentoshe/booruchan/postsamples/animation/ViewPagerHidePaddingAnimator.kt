package com.makentoshe.booruchan.postsamples.animation

import android.view.View
import com.makentoshe.booruchan.postpreviews.animations.ViewAnimator

class ViewPagerHidePaddingAnimator(
    target: View,
    private val paddingInPixels: Int,
    private val duration: Long
) : ViewAnimator<View>(target) {
    override fun animate() {
        target.animate().setDuration(duration)
            .setUpdateListener {
                val value = it.animatedValue as Float
                val padding = paddingInPixels * (1 - value)
                target.setPadding(0, padding.toInt(), 0, padding.toInt())
            }.start()
    }
}
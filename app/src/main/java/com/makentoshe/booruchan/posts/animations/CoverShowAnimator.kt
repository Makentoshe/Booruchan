package com.makentoshe.booruchan.posts.animations

import android.view.View

class CoverShowAnimator(
    target: View,
    private val duration: Long
): ViewAnimator<View>(target) {
    override fun animate() {
        target.animate().setDuration(duration)
            .setAnimListener {
                onAnimationStart {
                    target.visibility = View.VISIBLE
                }
            }
            .alpha(1f)
            .start()
        target.bringToFront()
    }
}
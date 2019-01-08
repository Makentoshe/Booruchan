package com.makentoshe.booruchan.posts.animations

import android.view.View

class CoverHideAnimator(
    target: View,
    private val duration: Long
): ViewAnimator<View>(target) {
    override fun animate() {
        target.animate().setDuration(duration)
            .setAnimListener {
                onAnimationEnd {
                    target.visibility = View.GONE
                }
            }
            .alpha(0f)
            .start()
    }
}
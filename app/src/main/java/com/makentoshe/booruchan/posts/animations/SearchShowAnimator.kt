package com.makentoshe.booruchan.posts.animations

import android.view.View

class SearchShowAnimator(target: View, private val duration: Long): ViewAnimator<View>(target) {
    override fun animate() {
        target.visibility = View.VISIBLE
        target.animate().setDuration(duration)
            .translationY(0f)
            .start()
    }
}
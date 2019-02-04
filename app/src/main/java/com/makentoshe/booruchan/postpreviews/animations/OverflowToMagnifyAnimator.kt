package com.makentoshe.booruchan.postpreviews.animations

import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.widget.ImageView
import com.makentoshe.style.Style

// Animates from current state to magnify icon
class OverflowToMagnifyAnimator(
    target: ImageView,
    private val style: Style
): ViewAnimator<ImageView>(target) {

    override fun animate() {
        val avd = target.context.getDrawable(style.drawable.animated.crossToMagnify) as AnimatedVectorDrawable
        avd.setColorFilter(style.toolbar.getOnPrimaryColor(target.context), PorterDuff.Mode.SRC_ATOP)
        target.setImageDrawable(avd)
        avd.start()
    }
}
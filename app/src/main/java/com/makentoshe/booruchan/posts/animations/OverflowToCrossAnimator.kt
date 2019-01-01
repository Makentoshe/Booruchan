package com.makentoshe.booruchan.posts.animations

import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.widget.ImageView
import com.makentoshe.booruchan.Style

// Animates from current state to cross icon
class OverflowToCrossAnimator(
    target: ImageView,
    private val style: Style
): ViewAnimator<ImageView>(target) {

    override fun animate() {
        val avd = target.context.getDrawable(style.drawable.animated.magnifyToCross) as AnimatedVectorDrawable
        avd.setColorFilter(style.toolbar.getOnPrimaryColor(target.context), PorterDuff.Mode.SRC_ATOP)
        target.setImageDrawable(avd)
        avd.start()
    }
}
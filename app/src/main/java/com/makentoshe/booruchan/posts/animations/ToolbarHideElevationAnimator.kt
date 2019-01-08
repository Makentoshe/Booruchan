package com.makentoshe.booruchan.posts.animations

import android.animation.ValueAnimator
import android.view.View
import org.jetbrains.anko.dip


class ToolbarHideElevationAnimator(target: View, private val duration: Long): ViewAnimator<View>(target) {
    override fun animate() {
        val animator = ValueAnimator.ofFloat(
            target.context.dip(4).toFloat(),
            target.context.dip(0).toFloat())
        animator.addUpdateListener {
            target.elevation = it.animatedValue as Float
        }
        animator.duration = duration
        animator.setAnimListener {
            onAnimationEnd {
                target.elevation = target.context.dip(0).toFloat()
            }
        }
        animator.start()
    }
}

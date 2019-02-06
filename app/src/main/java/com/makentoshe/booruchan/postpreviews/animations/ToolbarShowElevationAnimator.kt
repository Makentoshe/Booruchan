package com.makentoshe.booruchan.postpreviews.animations

import android.animation.ValueAnimator
import android.view.View
import org.jetbrains.anko.dip

class ToolbarShowElevationAnimator(target: View, private val duration: Long): ViewAnimator<View>(target) {
    override fun animate() {
        val animator = ValueAnimator.ofFloat(
            target.context.dip(0).toFloat(),
            target.context.dip(4).toFloat()
        )
        animator.addUpdateListener {
            target.elevation = it.animatedValue as Float
        }
        animator.duration = duration
        animator.setAnimListener {
            onAnimationEnd {
                target.elevation = target.context.dip(4).toFloat()
            }
        }
        animator.start()
    }
}
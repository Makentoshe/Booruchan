package com.makentoshe.booruchan.booru.model.animator

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

class AlphaLabelAnimator {

    fun show(view: View, onFinish: () -> (Unit)) {
        val a = ObjectAnimator.ofFloat(view, "alpha", 0f, 0.5f)
        a.duration = ViewAnimator.DURATION
        a.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                onFinish.invoke()
            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
                view.visibility = View.VISIBLE
            }
        })
        a.start()
    }

    fun hide(view: View, onFinish: () -> (Unit)) {
        val a = ObjectAnimator.ofFloat(view, "alpha", 0.5f, 0f)
        a.duration = ViewAnimator.DURATION
        a.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.GONE
                onFinish.invoke()
            }
        })
        a.start()
    }

}
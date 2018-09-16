package com.makentoshe.booruchan.booru.model.animator

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

class SearchLabelAnimator {

    private var translationStart = 0f
    private val translationEnd = 0f

    fun show(view: View, onFinish: () -> (Unit)) {
        translationStart = view.translationY
        val a = ObjectAnimator.ofFloat(view, "translationY", translationStart, translationEnd)
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
        val a = ObjectAnimator.ofFloat(view, "translationY", translationEnd, translationStart)
        a.duration = ViewAnimator.DURATION
        a.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.GONE
                onFinish.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
        a.start()
    }

}
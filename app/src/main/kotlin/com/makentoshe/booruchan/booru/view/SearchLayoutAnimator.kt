package com.makentoshe.booruchan.booru.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.support.v7.view.menu.ActionMenuItemView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.makentoshe.booruchan.Activity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.styles.Style

class SearchLayoutAnimator(private val searchView: View, private val searchViewAlpha: View,
                           private val activity: Activity, private val style: Style) {

    private val translationStart = searchView.translationY
    private val translationEnd = searchView.height.toFloat()
    private var lockToShow = false
    private var lockToHide = true
    private val duration = 200L
    private var isDisplay = false
    @Volatile
    private var firstIsFinish = false

    fun show() {
        if (!lockToShow) {
            lockToShow = true
            isDisplay = true
            changeIconToCross()
            searchViewShowAnimation()
            searchViewAlphaShowAnimation()
        }
    }

    fun hide(): Boolean {
        if (!lockToHide) {
            lockToHide = true
            isDisplay = false
            changeIconToMagnify()
            activity.hideKeyboard()
            searchViewHideAnimation()
            searchViewAlphaHideAnimation()
            return true
        }
        return false
    }

    fun isDisplaying(): Boolean {
        return isDisplay
    }

    private fun searchViewShowAnimation() {
        val a = ObjectAnimator.ofFloat(searchView, "translationY", translationStart, translationEnd)
        a.duration = duration
        a.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if (firstIsFinish) {
                    lockToHide = false
                    firstIsFinish = false
                } else {
                    firstIsFinish = true
                }
                searchView.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
                searchView.visibility = View.VISIBLE
            }
        })
        a.start()
    }

    private fun searchViewAlphaShowAnimation() {
        val a = ObjectAnimator.ofFloat(searchViewAlpha, "alpha", 0f, 0.5f)
        a.duration = duration
        a.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if (firstIsFinish) {
                    lockToHide = false
                    firstIsFinish = false
                } else {
                    firstIsFinish = true
                }
                searchView.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
                searchViewAlpha.visibility = View.VISIBLE
            }
        })
        a.start()
    }

    private fun searchViewHideAnimation() {
        val a = ObjectAnimator.ofFloat(searchView, "translationY", translationEnd, translationStart)
        a.duration = duration
        a.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if (firstIsFinish) {
                    lockToShow = false
                    firstIsFinish = false
                } else {
                    firstIsFinish = true
                }
                searchView.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
        a.start()
    }

    private fun searchViewAlphaHideAnimation() {
        val a = ObjectAnimator.ofFloat(searchViewAlpha, "alpha", 0.5f, 0f)
        a.duration = duration
        a.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if (firstIsFinish) {
                    lockToShow = false
                    firstIsFinish = false
                } else {
                    firstIsFinish = true
                }
                searchViewAlpha.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
        a.start()
    }

    @SuppressLint("RestrictedApi")
    private fun changeIconToMagnify() {
        val view: ActionMenuItemView = activity.findViewById(R.id.action_show_search)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeIconAVD(view, style.avdFromCrossToMagnify)
        } else {
            changeIcon(view, style.searchIcon)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun changeIconToCross() {
        val view: ActionMenuItemView = activity.findViewById(R.id.action_show_search)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeIconAVD(view, style.avdFromMagnifyToCross)
        } else {
            changeIcon(view, style.crossIcon)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    private fun changeIconAVD(view: ActionMenuItemView, @DrawableRes avd: Int) {
        val drawable = activity.resources.getDrawable(avd, activity.theme)
        view.setIcon(drawable)
        (drawable as AnimatedVectorDrawable).start()
    }

    @SuppressLint("RestrictedApi")
    private fun changeIcon(view: ActionMenuItemView, @DrawableRes end: Int) {
        val drawableEnd = activity.resources.getDrawable(end)

        val animation = AnimationUtils.loadAnimation(activity, R.anim.alpha_from_one)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                val animation2 = AnimationUtils.loadAnimation(activity, R.anim.alpha_to_one)
                view.alpha = 0f
                view.setIcon(drawableEnd)
                animation2.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {}

                    override fun onAnimationEnd(animation: Animation?) {
                        view.alpha = 1f
                    }

                    override fun onAnimationStart(animation: Animation?) {}

                })
                view.startAnimation(animation2)
            }

            override fun onAnimationStart(animation: Animation?) {}

        })
        view.startAnimation(animation)
    }
}
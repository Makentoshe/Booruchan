package com.makentoshe.booruchan.booru.model.animator

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.view.menu.ActionMenuItemView
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.styles.Style

class IconAnimator {

    @SuppressLint("NewApi")
    fun toCross(iconView: ActionMenuItemView, style: Style) {
        forLollipop({
            changeIconAVD(iconView, style.avdFromMagnifyToCross, style.toolbarForegroundColor)
        }, {
            changeIcon(iconView, style.avdFromMagnifyToCross, style.toolbarForegroundColor)
        })
    }

    @SuppressLint("NewApi")
    fun toMagnify(iconView: ActionMenuItemView, style: Style) {
        forLollipop ({
            changeIconAVD(iconView, style.avdFromCrossToMagnify, style.toolbarForegroundColor)
        }, {
            changeIcon(iconView, style.avdFromCrossToMagnify, style.toolbarForegroundColor)
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    private fun changeIconAVD(view: ActionMenuItemView, @DrawableRes avdRes: Int, @ColorRes colorRes: Int) {
        val drawable = view.context.resources.getDrawable(avdRes, view.context.theme)
        drawable.setColorFilter(ContextCompat.getColor(view.context, colorRes), PorterDuff.Mode.SRC_ATOP)
        view.setIcon(drawable)
        (drawable as AnimatedVectorDrawable).start()
    }

    @SuppressLint("RestrictedApi")
    private fun changeIcon(view: ActionMenuItemView, @DrawableRes end: Int, @ColorRes colorRes: Int) {
        val drawableEnd = view.context.resources.getDrawable(end)
        drawableEnd.setColorFilter(ContextCompat.getColor(view.context, colorRes), PorterDuff.Mode.SRC_ATOP)
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.alpha_from_one)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                val animation2 = AnimationUtils.loadAnimation(view.context, R.anim.alpha_to_one)
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
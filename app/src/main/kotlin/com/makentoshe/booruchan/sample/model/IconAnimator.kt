package com.makentoshe.booruchan.sample.model

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.styles.Style

class IconAnimator {

    @SuppressLint("NewApi")
    fun toCross(toolbar: Toolbar, style: Style) {
        forLollipop({
            changeIconAVD(toolbar, style.avdFromMenuToCross, style.toolbarForegroundColor)
        }, {
            changeIcon(toolbar, style.avdFromMenuToCross, style.toolbarForegroundColor)
        })
    }

    @SuppressLint("NewApi")
    fun toMenu(toolbar: Toolbar, style: Style) {
        forLollipop ({
            changeIconAVD(toolbar, style.avdFromCrossToMenu, style.toolbarForegroundColor)
        }, {
            changeIcon(toolbar, style.avdFromCrossToMenu, style.toolbarForegroundColor)
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    private fun changeIconAVD(toolbar: Toolbar, @DrawableRes avdRes: Int, @ColorRes colorRes: Int) {
        val drawable = toolbar.context.resources.getDrawable(avdRes, toolbar.context.theme)
        drawable.setColorFilter(ContextCompat.getColor(toolbar.context, colorRes), PorterDuff.Mode.SRC_ATOP)
        toolbar.navigationIcon = drawable
        (drawable as AnimatedVectorDrawable).start()
    }

    @SuppressLint("RestrictedApi")
    private fun changeIcon(toolbar: Toolbar, @DrawableRes end: Int, @ColorRes colorRes: Int) {
        val drawableEnd = toolbar.context.resources.getDrawable(end)
        drawableEnd.setColorFilter(ContextCompat.getColor(toolbar.context, colorRes), PorterDuff.Mode.SRC_ATOP)
        val animation = AnimationUtils.loadAnimation(toolbar.context, R.anim.alpha_from_one)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                val animation2 = AnimationUtils.loadAnimation(toolbar.context, R.anim.alpha_to_one)
                toolbar.alpha = 0f
                toolbar.navigationIcon = drawableEnd
                animation2.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) = Unit
                    override fun onAnimationEnd(animation: Animation?) {
                        toolbar.alpha = 1f
                    }
                    override fun onAnimationStart(animation: Animation?)= Unit

                })
                toolbar.startAnimation(animation2)
            }

            override fun onAnimationStart(animation: Animation?) {}

        })
        toolbar.startAnimation(animation)
    }
}
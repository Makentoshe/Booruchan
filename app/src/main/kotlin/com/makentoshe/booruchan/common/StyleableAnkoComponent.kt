package com.makentoshe.booruchan.common

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.AnkoComponent

abstract class StyleableAnkoComponent<T : AppCompatActivity>(protected val style: Style) : AnkoComponent<T> {

    protected fun Toolbar.setTitleTextColorResource(@ColorRes color: Int): Toolbar {
        setTitleTextColor(ContextCompat.getColor(context, color))
        return this
    }

    protected fun Toolbar.setOverflowIconColor(@ColorRes color: Int): Toolbar {
        overflowIcon?.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP)
        return this
    }

    protected fun Toolbar.setSupportActionBar(activity: AppCompatActivity): Toolbar {
        activity.setSupportActionBar(this)
        return this
    }

    protected fun Toolbar.setHomeIcon(@ColorRes colorRes: Int, activity: AppCompatActivity): Toolbar {
        val arrow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(R.drawable.abc_ic_ab_back_material, context.theme)
        } else {
            context.resources.getDrawable(R.drawable.abc_ic_ab_back_material)
        }
        val colorInt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(colorRes, context.theme)
        } else {
            context.resources.getColor(colorRes)
        }
        arrow.setColorFilter(colorInt, PorterDuff.Mode.SRC_ATOP)
        activity.supportActionBar?.apply {
            setHomeAsUpIndicator(arrow)
            setDisplayHomeAsUpEnabled(true)
           setHomeButtonEnabled(true)
        }
        return this
    }

}
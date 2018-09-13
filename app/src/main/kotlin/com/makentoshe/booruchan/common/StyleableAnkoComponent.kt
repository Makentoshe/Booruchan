package com.makentoshe.booruchan.common

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.AnkoComponent

abstract class StyleableAnkoComponent<T : AppCompatActivity>(protected val style: Style) : AnkoComponent<T> {

    fun setOverflowIconColor(@ColorRes color: Int, activity: T) {
        val decorView = activity.window.decorView as ViewGroup
        addOnGlobalLayoutListener(decorView) { listener ->
            val overflowDescription = activity.getString(R.string.abc_action_menu_overflow_description)
            val outViews = ArrayList<View>()
            decorView.findViewsWithText(outViews, overflowDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
            if (outViews.isNotEmpty()) {
                val overflow = outViews[0] as AppCompatImageView
                overflow.setColorFilter(ContextCompat.getColor(activity, color))
                removeOnGlobalLayoutListener(decorView, listener)
            }
        }
    }

    fun createHomeIcon(@ColorRes color: Int, activity: T): Drawable {
        val arrow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.resources.getDrawable(R.drawable.abc_ic_ab_back_material, activity.theme)
        } else {
            activity.resources.getDrawable(R.drawable.abc_ic_ab_back_material)
        }
        val colorInt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.resources.getColor(color, activity.theme)
        } else {
            activity.resources.getColor(color)
        }
        arrow.setColorFilter(colorInt, PorterDuff.Mode.SRC_ATOP)
        return arrow
    }

    private fun addOnGlobalLayoutListener(
            v: View, onGlobalLayout: (ViewTreeObserver.OnGlobalLayoutListener) -> Unit) {
        v.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                onGlobalLayout.invoke(this)
            }
        })
    }

    private fun removeOnGlobalLayoutListener(
            v: View, listener: ViewTreeObserver.OnGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.viewTreeObserver.removeGlobalOnLayoutListener(listener)
        } else {
            v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

}
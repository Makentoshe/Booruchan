package com.makentoshe.booruchan

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.AnkoComponent

abstract class StyleableAnkoComponent<T : AppCompatActivity>(protected val style: Style) : AnkoComponent<T> {

//    fun setOverflowIconColor(@ColorRes color: Int, activity: T) {
//        val decorView = activity.window.decorView as ViewGroup
//        addOnGlobalLayoutListener(decorView) { listener ->
//            val overflowDescription = activity.getString(R.string.abc_action_menu_overflow_description)
//            val outViews = ArrayList<View>()
//            decorView.findViewsWithText(outViews, overflowDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
//            if (outViews.isNotEmpty()) {
//                val overflow = outViews[0] as AppCompatImageView
//                overflow.setColorFilter(ContextCompat.getColor(activity, color))
//                removeOnGlobalLayoutListener(decorView, listener)
//            }
//        }
//    }
//
//    fun addOnGlobalLayoutListener(v: View, onGlobalLayout: (ViewTreeObserver.OnGlobalLayoutListener) -> Unit) {
//        v.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                onGlobalLayout.invoke(this)
//            }
//        })
//    }
//
//    fun removeOnGlobalLayoutListener(v: View, listener: ViewTreeObserver.OnGlobalLayoutListener) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//            v.viewTreeObserver.removeGlobalOnLayoutListener(listener)
//        } else {
//            v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
//        }
//    }

}
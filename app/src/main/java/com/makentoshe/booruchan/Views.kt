package com.makentoshe.booruchan

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7._Toolbar
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.custom.ankoView
import android.text.method.Touch.onTouchEvent
import android.view.MotionEvent
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import androidx.viewpager.widget.ViewPager
import android.R.attr.action
import kotlin.math.absoluteValue
import android.net.http.SslCertificate.saveState
import android.os.Bundle
import android.os.Parcelable




fun _RelativeLayout.toolbarLayout(
    toolbarInit: (@AnkoViewDslMarker _Toolbar).() -> Unit = {},
    imageViewInit: (@AnkoViewDslMarker ImageView).() -> Unit = {}
) {
    toolbar { toolbarInit() }.lparams(width = matchParent) { alignWithParent = true }
    imageView { imageViewInit() }.lparams(height = dip(24)) {
        setMargins(0, 0, dip(16), 0)
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }
}

fun hideKeyboard(context: Context, view: View) {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(view.windowToken, 0)
}

fun showKeyboard(context: Context, view: View) {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(view, 0)
}

fun View.getDrawable(@DrawableRes id: Int) = context.getDrawable(id)

open class _ChipGroup(ctx: Context) : ChipGroup(ctx) {

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = ChipGroup.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ChipGroup.LayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ChipGroup.LayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

}

fun ViewManager.chipGroup(init: _ChipGroup.() -> Unit) = ankoView({ _ChipGroup(it) }, 0, init)

fun ViewManager.chip(init: Chip.() -> Unit) = ankoView({ Chip(it) }, 0, init)

class VerticalViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    init {
        // The majority of the magic happens here
        setPageTransformer(true, VerticalPageTransformer())
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    private inner class VerticalPageTransformer : ViewPager.PageTransformer {

        override fun transformPage(view: View, position: Float) {
            when {
                position < -1 -> {// [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.alpha = 0f
                }

                position <= 1 -> { // [-1,1]
                    view.alpha = 1f

                    // Counteract the default slide transition
                    view.translationX = view.width * -position

                    //set Y position to swipe in from top
                    val yPosition = position * view.height
                    view.translationY = yPosition

                }
                else -> // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.alpha = 0f
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private fun swapXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()

        val newX = ev.y / height * width
        val newY = ev.x / width * height

        ev.setLocation(newX, newY)

        return ev
    }

    // Return true to steal motion events from the children and have
    // them dispatched to this ViewGroup through onTouchEvent()
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev) // return touch coordinates to original reference frame for any child views
        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent) = super.onTouchEvent(swapXY(ev))

}

class BlockableViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    var isBlocked = false

    // Return true to steal motion events from the children and have
    // them dispatched to this ViewGroup through onTouchEvent()
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (!isBlocked) handleInterceptTouchEvent(ev) else false
    }

    private var sx = -1f
    private var sy = -1f
    private fun handleInterceptTouchEvent(ev: MotionEvent): Boolean {
        when(ev.action) {
            MotionEvent.ACTION_DOWN -> {
                sx = ev.x
                sy = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = sx - ev.x
                val dy = sy - ev.y

                return dx.absoluteValue >= dy.absoluteValue && dx.absoluteValue > 25
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    //return true if the event was handled, false otherwise.
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (!isBlocked) super.onTouchEvent(ev) else false
    }
}

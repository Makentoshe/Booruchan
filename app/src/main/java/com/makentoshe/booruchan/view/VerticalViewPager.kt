package com.makentoshe.booruchan.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewManager
import androidx.viewpager.widget.ViewPager
import org.jetbrains.anko.custom.ankoView
import kotlin.math.absoluteValue

fun ViewManager.verticalViewPager(theme: Int = 0, init: VerticalViewPager.() -> Unit) =
    ankoView({ VerticalViewPager(it) }, theme, init)

open class VerticalViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

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

                    //tags Y position to swipe in from top
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
        val intercepted = handleInterceptTouchEvent(swapXY(ev))
        swapXY(ev) // return touch coordinates to original reference frame for any child views
        return intercepted
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

    override fun onTouchEvent(ev: MotionEvent) = super.onTouchEvent(swapXY(ev))

}
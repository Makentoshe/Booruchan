package com.makentoshe.booruchan

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class AnkoGestureListener : GestureDetector.OnGestureListener {

    private var onShowPress: ((MotionEvent?) -> Unit)? = null
    private var onSingleTapUp: ((MotionEvent?) -> Boolean)? = null
    private var onDown: ((MotionEvent?) -> Boolean)? = null
    private var onFling: ((MotionEvent?, MotionEvent?, Float, Float) -> Boolean)? = null
    private var onScroll: ((MotionEvent?, MotionEvent?, Float, Float) -> Boolean)? = null
    private var onLongPress: ((MotionEvent?) -> Unit)? = null

    override fun onShowPress(e: MotionEvent?) {
        onShowPress?.invoke(e)
    }

    fun onShowPress(action: (MotionEvent?) -> Unit) {
        onShowPress = action
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return onSingleTapUp?.invoke(e) ?: false
    }

    fun onSingleTapUp(action: (MotionEvent?) -> Boolean) {
        onSingleTapUp = action
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return onDown?.invoke(e) ?: false
    }

    fun onDown(action: (MotionEvent?) -> Boolean) {
        onDown = action
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return onFling?.invoke(e1, e2, velocityX, velocityY) ?: false
    }

    fun onFling(action: (MotionEvent?, MotionEvent?, Float, Float) -> Boolean) {
        onFling = action
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return onScroll?.invoke(e1, e2, distanceX, distanceY) ?: false
    }

    fun onScroll(action: (MotionEvent?, MotionEvent?, Float, Float) -> Boolean) {
        onScroll = action
    }

    override fun onLongPress(e: MotionEvent?) {
        onLongPress?.invoke(e)
    }

    fun onLongPress(action: (MotionEvent?) -> Unit) {
        onLongPress = action
    }
}


fun View.gestureDetector(init: AnkoGestureListener.() -> Unit) {
    val listener = AnkoGestureListener()
    listener.init()
    val detector = GestureDetector(context, listener)
    setOnTouchListener { _, event ->
        detector.onTouchEvent(event)
    }
}
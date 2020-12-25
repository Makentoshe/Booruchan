package com.makentoshe.booruchan.view

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


fun View.setGestureListener(init: CustomGestureListener.() -> Unit) {
    val listener = CustomGestureListener()
    listener.init()
    val detector = GestureDetector(context, listener)
    setOnTouchListener { _, event -> detector.onTouchEvent(event) }
}

class CustomGestureListener : GestureDetector.OnGestureListener {

    private var onShowPress: ((MotionEvent) -> Unit)? = null

    fun onShowPress(action: (MotionEvent) -> Unit) {
        onShowPress = action
    }

    override fun onShowPress(e: MotionEvent) {
        onShowPress?.invoke(e)
    }

    private var onSingleTapUp: ((MotionEvent) -> Boolean)? = null

    fun onSingleTapUp(action: (MotionEvent) -> Boolean) {
        onSingleTapUp = action
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return onSingleTapUp?.invoke(e) ?: false
    }

    private var onDown: ((MotionEvent) -> Boolean)? = null

    fun onDown(action: (MotionEvent) -> Boolean) {
        onDown = action
    }

    override fun onDown(e: MotionEvent): Boolean {
        return onDown?.invoke(e) ?: false
    }


    private var onLongPress: ((MotionEvent) -> Unit)? = null

    fun onLongPress(action: (MotionEvent) -> Unit) {
        onLongPress = action
    }

    override fun onLongPress(e: MotionEvent) {
        onLongPress?.invoke(e)
    }


    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float) = false

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float) = false
}

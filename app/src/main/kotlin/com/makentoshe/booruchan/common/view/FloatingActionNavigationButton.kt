package com.makentoshe.booruchan.common.view

import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.support.annotation.IntDef
import android.support.v7.widget.CardView
import android.view.View.OnClickListener
import com.makentoshe.booruchan.common.getScreenSize
import org.jetbrains.anko.dip
import java.util.*

class FloatingActionNavigationButton(context: Context) : CardView(context) {

    private val diameter = context.dip(56)
    private val stateChangedListenerList = LinkedList<OnStateChangedListener>()

    companion object {
        const val STATE_EXPAND = 1
        const val STATE_COLLAPSE = 0
    }

    @IntDef(STATE_COLLAPSE, STATE_EXPAND)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class State

    var blockExpand = false

    var state = STATE_COLLAPSE
        private set

    init {
        radius = diameter / 2f
        setOnClickListener(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = dip(4).toFloat()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        val listener = OnClickListener {
            l?.onClick(it)
            if (state == STATE_COLLAPSE && !blockExpand) expandView() else collapseView()
        }
        super.setOnClickListener(listener)
    }

    fun expandView() {
        if (blockExpand) return
        changeViewWidthInAnimation(getScreenSize(context).width)
        changeState(STATE_EXPAND)
    }

    fun collapseView() {
        changeViewWidthInAnimation(diameter)
        changeState(STATE_COLLAPSE)
    }

    private fun changeViewWidthInAnimation(widthResult: Int) {
        val anim = ValueAnimator.ofInt(measuredWidth, widthResult)
        anim.addUpdateListener { valueAnimator ->
            val layoutParams = layoutParams
            layoutParams.width = valueAnimator.animatedValue as Int
            setLayoutParams(layoutParams)
        }
        anim.duration = 200
        anim.start()
    }

    private fun changeState(@State state: Int) {
        when (state) {
            STATE_COLLAPSE -> {
                this.state = STATE_COLLAPSE
                stateChangedListenerList.forEach { it.onCollapse(this) }
            }
            STATE_EXPAND -> {
                this.state = STATE_EXPAND
                stateChangedListenerList.forEach { it.onExpand(this) }
            }
        }
    }

    fun addOnStateChangedListener(listener: OnStateChangedListener) {
        stateChangedListenerList.add(listener)
    }

    fun addOnExpandListener(onExpand: (v: FloatingActionNavigationButton) -> (Unit)) {
        stateChangedListenerList.add(object : OnStateChangedListener {
            override fun onExpand(v: FloatingActionNavigationButton) = onExpand(v)
            override fun onCollapse(v: FloatingActionNavigationButton) = Unit
        })
    }

    fun addOnCollapseListener(onCollapse: (v: FloatingActionNavigationButton) -> (Unit)) {
        stateChangedListenerList.add(object : OnStateChangedListener {
            override fun onExpand(v: FloatingActionNavigationButton) = Unit
            override fun onCollapse(v: FloatingActionNavigationButton) = onCollapse(v)
        })
    }

    fun removeOnStateChangedListener(listener: OnStateChangedListener) {
        stateChangedListenerList.remove(listener)
    }

    fun removeAllOnStateChangedListener() {
        stateChangedListenerList.clear()
    }

    interface OnStateChangedListener {

        fun onExpand(v: FloatingActionNavigationButton)

        fun onCollapse(v: FloatingActionNavigationButton)
    }

}
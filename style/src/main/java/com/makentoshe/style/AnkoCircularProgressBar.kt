package com.makentoshe.style

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.annotation.StyleRes
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.circularProgressBar(
    @StyleRes theme: Int = 0, init: AnkoCircularProgressBar.() -> Unit
): CircularProgressBar {
    return ankoView({ AnkoCircularProgressBar(it) }, theme, init)
}

inline fun AnkoContext<*>.circularProgressBar(
    @StyleRes theme: Int = 0, init: AnkoCircularProgressBar.() -> Unit
): CircularProgressBar {
    return ankoView({ AnkoCircularProgressBar(it) }, theme, init)
}

open class AnkoCircularProgressBar(context: Context) : CircularProgressBar(context) {

    fun <T : View> T.lparams(
        width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        init: FrameLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = FrameLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = FrameLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }
}

open class CircularProgressBar constructor(context: Context) : View(context) {

    private val mStartAngle = -90f              // Always start from top (default is: "3 o'clock on a watch.")
    private var mSweepAngle = 0f                // How long to sweep from mStartAngle
    private val mMaxSweepAngle = 360f           // Max degrees to sweep = full circle
    private var mStrokeWidth = 16               // Width of outline
    private val mAnimationDuration = 400        // Animation duration for progress change
    private val mMaxProgress = 100              // Max progress to use
    private var mRoundedCorners = true          // Set to true if rounded corners should be applied to outline ends
    private var mProgressColor = Color.BLACK    // Outline color

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val shape by lazy {
        val diameter = Math.min(width, height)
        val pad = (mStrokeWidth / 2.0).toFloat()
        RectF(pad, pad, diameter - pad, diameter - pad)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.color = mProgressColor
        mPaint.strokeWidth = mStrokeWidth.toFloat()
        mPaint.isAntiAlias = true
        mPaint.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(shape, mStartAngle, mSweepAngle, false, mPaint)
    }

    fun setProgress(@IntRange(from = 0, to = 100) progress: Int) {
        ValueAnimator.ofFloat(mSweepAngle, mMaxSweepAngle / mMaxProgress * progress).apply {
            interpolator = DecelerateInterpolator()
            duration = mAnimationDuration.toLong()
            addUpdateListener { valueAnimator -> mSweepAngle = valueAnimator.animatedValue as Float; invalidate() }
            start()
        }
    }

    fun setProgressColor(color: Int) {
        mProgressColor = color
        invalidate()
    }

    fun setProgressWidth(width: Int) {
        mStrokeWidth = width
        invalidate()
    }

    fun useRoundedCorners(roundedCorners: Boolean) {
        mRoundedCorners = roundedCorners
        invalidate()
    }
}


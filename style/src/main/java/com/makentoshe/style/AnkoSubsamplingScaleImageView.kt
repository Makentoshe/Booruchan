package com.makentoshe.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.FrameLayout
import androidx.annotation.StyleRes
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.subsamplingScaleImageView(
    @StyleRes theme: Int = 0, init: AnkoSubsamplingScaleImageView.() -> Unit
): SubsamplingScaleImageView {
    return ankoView({ AnkoSubsamplingScaleImageView(it) }, theme, init)
}

inline fun AnkoContext<*>.subsamplingScaleImageView(
    @StyleRes theme: Int = 0, init: AnkoSubsamplingScaleImageView.() -> Unit
): SubsamplingScaleImageView {
    return ankoView({ AnkoSubsamplingScaleImageView(it) }, theme, init)
}

open class AnkoSubsamplingScaleImageView(context: Context) : SubsamplingScaleImageView(context) {

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
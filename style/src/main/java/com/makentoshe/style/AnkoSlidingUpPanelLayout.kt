package com.makentoshe.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.annotation.StyleRes
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.slidingUpPanel(@StyleRes theme: Int = 0, init: AnkoSlidingUpPanelLayout.() -> Unit): SlidingUpPanelLayout {
    return ankoView({ AnkoSlidingUpPanelLayout(it) }, theme, init)
}

inline fun AnkoContext<*>.slidingUpPanel(@StyleRes theme: Int = 0, init: AnkoSlidingUpPanelLayout.() -> Unit): SlidingUpPanelLayout {
    return ankoView({ AnkoSlidingUpPanelLayout(it) }, theme, init)
}

open class AnkoSlidingUpPanelLayout(ctx: Context) : SlidingUpPanelLayout(ctx) {

    fun <T : View> T.lparams(
        width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        init: LayoutParams.() -> Unit
    ): T {
        val layoutParams = LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

}

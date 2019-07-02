package com.makentoshe.style

import android.content.Context
import android.view.View
import android.view.ViewManager
import androidx.annotation.StyleRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.swipeRefreshLayout(
    @StyleRes theme: Int = 0, init: AnkoSwipeRefreshLayout.() -> Unit
): SwipeRefreshLayout {
    return ankoView({ AnkoSwipeRefreshLayout(it) }, theme, init)
}

inline fun AnkoContext<*>.swipeRefreshLayout(
    @StyleRes theme: Int = 0, init: AnkoSwipeRefreshLayout.() -> Unit
): SwipeRefreshLayout {
    return ankoView({ AnkoSwipeRefreshLayout(it) }, theme, init)
}

class AnkoSwipeRefreshLayout(context: Context) : SwipeRefreshLayout(context) {

    fun <T : View> T.lparams(
        width: Int = LayoutParams.WRAP_CONTENT,
        height: Int = LayoutParams.WRAP_CONTENT,
        init: LayoutParams.() -> Unit
    ): T {
        val layoutParams = LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        width: Int = LayoutParams.WRAP_CONTENT,
        height: Int = LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

}
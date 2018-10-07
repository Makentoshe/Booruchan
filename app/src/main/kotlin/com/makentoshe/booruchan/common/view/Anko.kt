package com.makentoshe.booruchan.common.view

import android.annotation.SuppressLint
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.custom.ankoView
import kotlin.coroutines.experimental.CoroutineContext

inline fun ViewManager.verticalViewPager(init: VerticalViewPager.() -> Unit) = ankoView({ VerticalViewPager(it) }, 0, init)

inline fun ViewManager.delayAutocompleteEditText(init: DelayAutocompleteEditText.() -> Unit) = ankoView({ DelayAutocompleteEditText(it) }, 0, init)

inline fun ViewManager.roundedImageView(init: RoundedImageView.() -> Unit) = ankoView({ RoundedImageView(it) }, 0, init)

@SuppressLint("NewApi")
inline fun ViewManager.floatingActionNavigationButton(init: FloatingActionNavigationButton.() -> Unit) = ankoView({ FloatingActionNavigationButton(it) }, 0, init)

fun ViewManager.floatingActionNavigationButton() = floatingActionNavigationButton {}

fun RecyclerView.onScroll(
        context: CoroutineContext = UI,
        handler: suspend CoroutineScope.(v: RecyclerView?, dx: Int, dy: Int) -> Unit) {

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            CoroutineScope(context).launch {
                handler(recyclerView, dx, dy)
            }
        }
    })
}

fun SwipeRefreshLayout.onSwipe(
        context: CoroutineContext = UI,
        handler: suspend CoroutineScope.() -> Unit) {
    setOnRefreshListener { CoroutineScope(context).launch { handler() } }
}
package com.makentoshe.booruchan.common

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewManager
import android.view.inputmethod.InputMethodManager
import com.makentoshe.booruchan.common.view.DelayAutocompleteEditText
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.custom.ankoView
import kotlin.coroutines.experimental.CoroutineContext

inline fun forSdk(sdk: Int, `do`: () -> Unit, `else`: () -> Unit) {
    if (Build.VERSION.SDK_INT >= sdk) `do`.invoke() else `else`.invoke()
}

inline fun forLollipop(`do`: () -> Unit, `else`: () -> Unit) {
    forSdk(Build.VERSION_CODES.LOLLIPOP, `do`, `else`)
}

inline fun forLollipop(`do`: () -> Unit) {
    forSdk(Build.VERSION_CODES.LOLLIPOP, `do`, {})
}

fun hideKeyboard(activity: AppCompatActivity) {
    val imm = activity.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

inline fun ViewManager.delayAutocompleteEditText(init: DelayAutocompleteEditText.() -> Unit) = ankoView({ DelayAutocompleteEditText(it) }, 0, init)

inline fun ViewManager.roundedImageView(init: RoundedImageView.() -> Unit) = ankoView({ RoundedImageView(it) }, 0, init)

fun runOnUi(action: () -> (Unit)) {
    Handler(Looper.getMainLooper()).post(action)
}

fun runOnUiSuspend(action: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(UI).launch { action() }
}

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
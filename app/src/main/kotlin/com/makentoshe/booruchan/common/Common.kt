package com.makentoshe.booruchan.common

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

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

fun getScreenSize(context: Context): ScreenSize {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val size = Point()
    wm.defaultDisplay.getSize(size)
    return ScreenSize(size.x, size.y)
}

data class ScreenSize(val width: Int, val height: Int)

fun runOnUi(action: () -> (Unit)) {
    Handler(Looper.getMainLooper()).post(action)
}
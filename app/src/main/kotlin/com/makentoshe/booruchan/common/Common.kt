package com.makentoshe.booruchan.common

import android.os.Build

inline fun forSdk(sdk: Int, `do`: () -> Unit, `else`: () -> Unit) {
    if (Build.VERSION.SDK_INT >= sdk) `do`.invoke() else `else`.invoke()
}

inline fun forLollipop(`do`: () -> Unit, `else`: () -> Unit) {
    forSdk(Build.VERSION_CODES.LOLLIPOP, `do`, `else`)
}

inline fun forLollipop(`do`: () -> Unit) {
    forSdk(Build.VERSION_CODES.LOLLIPOP, `do`, {})
}
package com.makentoshe.booruchan.view

import android.view.View
import android.view.ViewGroup

inline fun <reified T : ViewGroup.LayoutParams> View.updateLayoutParams(block: T.() -> Unit) : View {
    val params = layoutParams as T
    block(params)
    layoutParams = params
    return this
}
package com.makentoshe.booruchan.style

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes

interface Style {

    @get:StyleRes
    val toolbar: Int

    @get:StyleRes
    val main: Int

    @get:StyleRes
    val progress: Int

    @get:StyleRes
    val default: Int

    @get:StyleRes
    val dialog: Int

    @get:StyleRes
    val text: Int
}

@ColorInt
fun Context.getColorFromStyle(resid: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(resid, typedValue, true)
    return typedValue.data
}

@ColorInt
fun View.getColorFromStyle(resid: Int): Int {
    return context.getColorFromStyle(resid)
}
package com.makentoshe.booruchan.style

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import com.makentoshe.booruchan.R

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

}

class SotisStyle : Style {

    override val toolbar: Int
        get() = R.style.SotisToolbar

    override val main: Int
        get() = R.style.Sotis

    override val progress: Int
        get() = R.style.SotisProgress

    override val default: Int
        get() = R.style.SotisDefault

    override val dialog: Int
        get() = R.style.Dialog
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
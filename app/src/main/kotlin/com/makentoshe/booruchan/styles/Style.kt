package com.makentoshe.booruchan.styles

import android.support.annotation.ColorRes
import android.support.annotation.IntDef
import com.makentoshe.booruchan.R

interface Style {

    @IntDef(Style.Astarte, Style.Shuvi)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class StyleVal

    companion object {
        @StyleVal
        const val Astarte = R.style.Astarte

        @StyleVal
        const val Shuvi = R.style.Shuvi
    }

    @get:ColorRes
    val toolbarTextColor: Int

    @get:ColorRes
    val toolbarBackgroundColor: Int

    val dpToolbarHeight: Int
        get() = 56
}
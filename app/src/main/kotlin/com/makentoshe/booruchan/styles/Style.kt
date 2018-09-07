package com.makentoshe.booruchan.styles

import android.support.annotation.ColorRes
import android.support.annotation.IntDef

interface Style {

    @IntDef(Style.Astarte)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class StyleVal

    companion object {
        @StyleVal
        const val Astarte = 0
    }

    @get:ColorRes
    val toolbarTextColor: Int

    @get:ColorRes
    val toolbarBackgroundColor: Int

    val dpToolbarHeight: Int
        get() = 56
}
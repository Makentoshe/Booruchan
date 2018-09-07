package com.makentoshe.booruchan.styles

import android.support.annotation.ColorRes
import android.support.annotation.IntDef
import android.support.annotation.StringDef
import com.makentoshe.booruchan.R

interface Style {

    @IntDef(Style.Astarte, Style.Shuvi)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class StyleVal

    @StringDef(Style.AstarteName, Style.ShuviName)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class StyleName

    companion object {
        @StyleVal
        const val Astarte = R.style.Astarte
        @StyleName
        const val AstarteName = "Astarte"

        @StyleVal
        const val Shuvi = R.style.Shuvi
        @StyleName
        const val ShuviName = "Shuvi"

        val arrayOfStyleNames = arrayOf(AstarteName, ShuviName)

        fun getStyleIndex(@StyleVal value: Int): Int {
            return when(value) {
                Astarte -> 0
                Shuvi -> 1
                else -> throw IllegalArgumentException()
            }
        }

        fun getStyleValByName(@StyleName name: String): Int {
            return when(name) {
                AstarteName -> Astarte
                ShuviName -> Shuvi
                else -> throw IllegalArgumentException()
            }
        }
    }

    @get:ColorRes
    val toolbarTextColor: Int

    @get:ColorRes
    val toolbarBackgroundColor: Int

    val dpToolbarHeight: Int
        get() = 56
}
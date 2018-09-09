package com.makentoshe.booruchan.styles

import android.support.annotation.ColorRes
import android.support.annotation.IntDef
import android.support.annotation.StringDef
import com.makentoshe.booruchan.R

interface Style {

    @StyleID
    val styleId: Int

    @StyleName
    val styleName: String

    @IntDef(Style.Astarte, Style.Shuvi)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class StyleID

    @StringDef(Style.AstarteName, Style.ShuviName)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class StyleName

    companion object {
        @StyleID
        const val Astarte = R.style.Astarte
        @StyleName
        const val AstarteName = "Astarte"

        @StyleID
        const val Shuvi = R.style.Shuvi
        @StyleName
        const val ShuviName = "Shuvi"

        @StyleID
        const val Rin = R.style.Rin
        @StyleName
        const val RinName = "Rin"

        val arrayOfStyleNames = arrayOf(AstarteName, ShuviName, RinName)

        fun getStyleIndex(@StyleID value: Int): Int {
            return when (value) {
                Astarte -> 0
                Shuvi -> 1
                Rin -> 2
                else -> throw IllegalArgumentException()
            }
        }

        fun getStyleValByName(@StyleName name: String): Int {
            return when (name) {
                AstarteName -> Astarte
                ShuviName -> Shuvi
                RinName -> Rin
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

    @get:ColorRes
    val backgroudColor: Int
        get() = android.R.color.white
}
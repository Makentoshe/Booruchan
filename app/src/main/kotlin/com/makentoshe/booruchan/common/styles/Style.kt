package com.makentoshe.booruchan.common.styles

import android.support.annotation.*
import com.makentoshe.booruchan.R

interface Style {

    companion object {
        const val Astarte = R.style.Astarte
        const val AstarteName = "Astarte"

        const val Shuvi = R.style.Shuvi
        const val ShuviName = "Shuvi"

        const val Rin = R.style.Rin
        const val RinName = "Rin"

        val arrayOfStyleNames = arrayOf(AstarteName, ShuviName, RinName)

        fun getStyleIndex(@StyleRes value: Int): Int {
            return when (value) {
                Astarte -> 0
                Shuvi -> 1
                Rin -> 2
                else -> throw IllegalArgumentException()
            }
        }

        fun getStyleIdByName(name: String): Int {
            return when (name) {
                AstarteName -> Astarte
                ShuviName -> Shuvi
                RinName -> Rin
                else -> throw IllegalArgumentException()
            }
        }

        fun getStyleByName(name: String): Style {
            return when (name) {
                AstarteName -> AstarteStyle()
                ShuviName -> ShuviStyle()
                RinName -> RinStyle()
                else -> throw IllegalArgumentException()
            }
        }
    }

    @get:StyleRes
    val styleId: Int

    @get:ColorRes
    val assentColor: Int

    @get:ColorRes
    val toolbarForegroundColor: Int

    @get:ColorRes
    val toolbarBackgroundColor: Int

    val dpToolbarHeight: Int
        get() = 56

    @get:ColorRes
    val backgroundColor: Int
        get() = android.R.color.white

    @get:DrawableRes
    val searchIcon: Int
        get() = R.drawable.ic_magnify_vector_white

    @get:DrawableRes
    val clearIcon: Int
        get() = R.drawable.ic_close_vector_black

    @get:DrawableRes
    val crossIcon: Int
        get() = R.drawable.ic_close_vector_white

    @get:DrawableRes
    val avdFromMagnifyToCross: Int

    @get:DrawableRes
    val avdFromCrossToMagnify: Int
}
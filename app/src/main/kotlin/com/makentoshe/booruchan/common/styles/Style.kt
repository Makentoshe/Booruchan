package com.makentoshe.booruchan.common.styles

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import com.makentoshe.booruchan.R
import java.io.Serializable

interface Style : Serializable {

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

        fun getStyleByName(name: String): Style {
            return when (name) {
                AstarteName -> AstarteStyle()
                ShuviName -> ShuviStyle()
                RinName -> RinStyle()
                else -> throw IllegalArgumentException()
            }
        }

        const val dpToolbarHeight = 56
    }

    @get:StyleRes
    val styleId: Int

    @get:ColorRes
    val toolbarForegroundColor: Int

    @get:ColorRes
    val toolbarBackgroundColor: Int


    val dpToolbarHeight: Int
        get() = 56

    @get:ColorRes
    val backgroundColor: Int
        get() = android.R.color.white

    @get:ColorRes
    val assentSecondaryColor: Int

    @get:ColorRes
    val hintColor: Int

    @get:DrawableRes
    val avdFromCrossToMagnify: Int
        get() = R.drawable.avd_close_magnify_vector_white

    @get:DrawableRes
    val avdFromMagnifyToCross: Int
        get() = R.drawable.avd_magnify_close_vector_white

    @get:DrawableRes
    val iconArrowUp: Int
        get() = R.drawable.ic_arrow_vector

    @get:DrawableRes
    val avdFromMenuToCross: Int
        get() = R.drawable.avd_menu_close_vector

    @get:DrawableRes
    val avdFromCrossToMenu
        get() = R.drawable.avd_close_menu_vector
}
package com.makentoshe.booruchan.common.styles

import android.content.Context
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import com.makentoshe.booruchan.R
import org.jetbrains.anko.dip

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

    val toolbar: ToolbarStyle

    val view: ViewStyle

    val floatingActionButton: FloatingActionButtonStyle

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
}

open class ViewStyle(@JvmField @ColorRes val primaryColorRes: Int,
                     @JvmField @ColorRes val onPrimaryColorRes: Int) {

    fun getPrimaryColor(context: Context) = ContextCompat.getColor(context, primaryColorRes)

    fun getOnPrimaryColor(context: Context) = ContextCompat.getColor(context, onPrimaryColorRes)

}

class ToolbarStyle(primaryColorRes: Int, onPrimaryColorRes: Int) : ViewStyle(primaryColorRes, onPrimaryColorRes) {

    @JvmField val dpHeight = 56

    fun getHeightInPixel(context: Context) = context.dip(dpHeight)

}

class FloatingActionButtonStyle(primaryColorRes: Int, onPrimaryColorRes: Int) :ViewStyle(primaryColorRes, onPrimaryColorRes)

class BackdropStyle(primaryColorRes: Int, onPrimaryColorRes: Int): ViewStyle(primaryColorRes, onPrimaryColorRes)

class ChipStyle(primaryColorRes: Int, onPrimaryColorRes: Int): ViewStyle(primaryColorRes, onPrimaryColorRes)
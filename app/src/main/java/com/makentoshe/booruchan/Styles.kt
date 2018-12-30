package com.makentoshe.booruchan

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import java.io.Serializable

abstract class Style : Serializable {

    abstract val name: String
    @get:StyleRes
    abstract val id: Int

    abstract val toolbar: Toolbar
    abstract val assent: Assent
    abstract val background: Background
    abstract val chip: Chip
    val drawable = Drawable()

    abstract class ViewStyle(@JvmField @ColorRes val primaryColorRes: Int,
                             @JvmField @ColorRes val onPrimaryColorRes: Int,
                             @JvmField @ColorRes val secondaryColorRes: Int = primaryColorRes,
                             @JvmField @ColorRes val onSecondaryColorRes: Int = onPrimaryColorRes) {

        fun getPrimaryColor(context: Context) = ContextCompat.getColor(context, primaryColorRes)

        fun getOnPrimaryColor(context: Context) = ContextCompat.getColor(context, onPrimaryColorRes)

        fun getSecondaryColor(context: Context) = ContextCompat.getColor(context, secondaryColorRes)

        fun getOnSecondaryColor(context: Context) = ContextCompat.getColor(context, onSecondaryColorRes)

    }

    class Toolbar(primaryColorRes: Int, onPrimaryColorRes: Int) : ViewStyle(primaryColorRes, onPrimaryColorRes)

    class Assent(
        @JvmField @ColorRes val assentColorRes: Int,
        @JvmField @ColorRes val onAssentColorRes: Int,
        @JvmField @ColorRes val secondaryAssentColorRes: Int,
        @JvmField @ColorRes val onSecondaryAssentColorRes: Int) {

        fun getAssentColor(context: Context) = ContextCompat.getColor(context, assentColorRes)

        fun getOnAssentColor(context: Context) = ContextCompat.getColor(context, onAssentColorRes)

        fun getSecondaryAssentColor(context: Context) = ContextCompat.getColor(context, secondaryAssentColorRes)

        fun getOnSecondaryAssentColor(context: Context) = ContextCompat.getColor(context, onSecondaryAssentColorRes)

    }

    class Background(@JvmField val backgroundColorRes: Int, @JvmField val onBackgroundColorRes: Int) {

        fun getBackgroundColor(context: Context) = ContextCompat.getColor(context, backgroundColorRes)

        fun getOnBackgroundColor(context: Context) = ContextCompat.getColor(context, onBackgroundColorRes)

    }

    class Chip(@ColorRes primaryColorRes: Int,
               @ColorRes onPrimaryColorRes: Int,
               @ColorRes secondaryColorRes: Int,
               @ColorRes onSecondaryColorRes: Int)
        : ViewStyle(primaryColorRes, onPrimaryColorRes, secondaryColorRes, onSecondaryColorRes)

    class Drawable {

        @JvmField
        val animated = Animated()

        @JvmField
        val static = Static()

        companion object {

            class Animated {

                @JvmField
                @DrawableRes
                val crossToMagnify = R.drawable.avd_cross_magnify

                @JvmField
                @DrawableRes
                val magnifyToCross = R.drawable.avd_magnify_cross
            }

            class Static {
                @JvmField
                @DrawableRes
                val magnify = R.drawable.avd_cross_magnify

                @JvmField
                @DrawableRes
                val cross = R.drawable.avd_magnify_cross

                @JvmField
                @DrawableRes
                val chevron = R.drawable.ic_chevron_vector

                @JvmField
                @DrawableRes
                val overflow = R.drawable.ic_overflow

                @JvmField
                @DrawableRes
                val menu = R.drawable.ic_menu_vector
            }
        }

    }

    companion object {
        @JvmStatic
        fun getStyle(@StyleRes styleRes: Int) = when (styleRes) {
            R.style.Sotis -> SotisStyle()
            R.style.Astarte -> AstarteStyle()
            else -> throw IllegalArgumentException("The param \"$styleRes\" is not one of the declared:\n" +
                    "Sotis - ${R.style.Sotis}\n" +
                    "Astarte - ${R.style.Astarte}")
        }

        @JvmStatic
        fun getStyle(name: String) = when (name) {
            "Sotis" -> SotisStyle()
            "Astarte" -> AstarteStyle()
            else -> throw IllegalArgumentException("The param \"$name\" is not one of the declared:\n" +
                    "Sotis, Astarte")
        }

        @JvmStatic
        val arrayOfStyleNames = arrayOf("Sotis", "Astarte")
    }
}

class SotisStyle : Style(), Serializable {
    override val id = R.style.Sotis
    override val name = "Sotis"
    override val toolbar = Toolbar(R.color.MaterialTeal600, android.R.color.white)
    override val assent = Assent(R.color.TealAssent200, android.R.color.white,
        R.color.MaterialTeal900, android.R.color.white)
    override val background = Background(android.R.color.white, android.R.color.black)
    override val chip = Chip(R.color.MaterialTeal900, android.R.color.white,
        R.color.MaterialPink800, android.R.color.white)
}

class AstarteStyle : Style(), Serializable {
    override val id = R.style.Astarte
    override val name = "Astarte"
    override val toolbar = Toolbar(R.color.MaterialIndigo500, android.R.color.white)
    override val assent = Assent(R.color.IndigoAssent200, android.R.color.widget_edittext_dark,
        R.color.MaterialIndigo700, android.R.color.white)
    override val background = Background(android.R.color.white, android.R.color.black)
    override val chip = Chip(R.color.MaterialIndigo700, android.R.color.white,
        R.color.MaterialPink800, android.R.color.white)
}
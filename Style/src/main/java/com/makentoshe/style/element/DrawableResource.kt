package com.makentoshe.style.element

import androidx.annotation.DrawableRes
import com.makentoshe.style.R

class DrawableResource {

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

            @JvmField
            @DrawableRes
            val download = R.drawable.ic_download

            @JvmField
            @DrawableRes
            val arrow = R.drawable.ic_arrow
        }
    }

}
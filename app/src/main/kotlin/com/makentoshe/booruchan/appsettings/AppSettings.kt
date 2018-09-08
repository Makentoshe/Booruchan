package com.makentoshe.booruchan.appsettings

import com.makentoshe.booruchan.styles.AstarteStyle
import com.makentoshe.booruchan.styles.RinStyle
import com.makentoshe.booruchan.styles.ShuviStyle
import com.makentoshe.booruchan.styles.Style
import java.lang.IllegalArgumentException

class AppSettings {

    companion object {
        const val NAME = "ApplicationSettings"
    }

    private var style: Style = AstarteStyle()
    private var styleVal: Int = Style.Astarte

    fun setStyle(@Style.StyleID value: Int) {
        when(value) {
            Style.Astarte -> {
                checkStyleValuesIsValid(value) {
                    replaceStyleValues(AstarteStyle(), value)
                }
            }
            Style.Shuvi -> {
                checkStyleValuesIsValid(value) {
                    replaceStyleValues(ShuviStyle(), value)
                }
            }
            Style.Rin -> {
                checkStyleValuesIsValid(value) {
                    replaceStyleValues(RinStyle(), value)
                }
            }
            else -> throw IllegalArgumentException("Theme code $value is not defined.")
        }
    }

    private inline fun checkStyleValuesIsValid(styleVal: Int, ok: () -> Unit) {
        if (styleVal != this.styleVal) ok.invoke()
    }

    private fun replaceStyleValues(style: Style, styleVal: Int) {
        this.style = style
        this.styleVal = styleVal
    }

    fun getStyle(): Style {
        return style
    }

    fun getStyleVal(): Int {
        return styleVal
    }

}


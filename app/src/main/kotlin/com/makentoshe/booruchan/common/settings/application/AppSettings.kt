package com.makentoshe.booruchan.common.settings.application

import com.makentoshe.booruchan.common.styles.AstarteStyle
import com.makentoshe.booruchan.common.styles.RinStyle
import com.makentoshe.booruchan.common.styles.ShuviStyle
import com.makentoshe.booruchan.common.styles.Style

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
            else -> replaceStyleValues(AstarteStyle(), value)
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


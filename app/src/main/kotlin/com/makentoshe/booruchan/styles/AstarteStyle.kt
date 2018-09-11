package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R

class AstarteStyle: Style {
    override val avdFromCrossToMagnify: Int
        get() = R.drawable.avd_close_magnify_vector_white

    override val avdFromMagnifyToCross: Int
        get() = R.drawable.avd_magnify_close_vector_white

    override val assentColor: Int
        get() = R.color.IndigoAssent200

    @Style.StyleID
    override val styleId: Int
        get() = Style.Astarte

    @Style.StyleName
    override val styleName: String
        get() = Style.AstarteName

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialIndigo500

    override val toolbarForegroundColor: Int
        get() = android.R.color.white

}
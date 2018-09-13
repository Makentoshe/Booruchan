package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R

class RinStyle : Style {

    override val avdFromCrossToMagnify: Int
        get() = R.drawable.avd_close_magnify_vector_black

    override val avdFromMagnifyToCross: Int
        get() = R.drawable.avd_magnify_close_vector_black

    override val assentColor: Int
        get() = android.R.color.black

    @Style.StyleID
    override val styleId: Int
        get() = Style.Rin

    @Style.StyleName
    override val styleName: String
        get() = Style.RinName

    override val toolbarForegroundColor: Int
        get() = android.R.color.black

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialYellow500

    override val searchIcon: Int
        get() = R.drawable.ic_magnify_vector_black

    override val crossIcon: Int
        get() = R.drawable.ic_close_vector_black
}
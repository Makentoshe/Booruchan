package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R

class RinStyle : Style {

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
}
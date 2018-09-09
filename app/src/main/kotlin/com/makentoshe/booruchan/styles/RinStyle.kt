package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R

class RinStyle : Style {

    @Style.StyleID
    override val styleId: Int
        get() = Style.Rin

    @Style.StyleName
    override val styleName: String
        get() = Style.RinName

    override val toolbarForegroundColor: Int
        get() = android.R.color.white

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialYellow500
}
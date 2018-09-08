package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R

class AstarteStyle: Style {

    @Style.StyleID
    override val styleId: Int
        get() = Style.Astarte

    @Style.StyleName
    override val styleName: String
        get() = Style.AstarteName

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialIndigo500

    override val toolbarTextColor: Int
        get() = android.R.color.white

}
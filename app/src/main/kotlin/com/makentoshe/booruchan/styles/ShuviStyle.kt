package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R

class ShuviStyle : Style {
    override val assentColor: Int
        get() = R.color.PurpleAssent200

    @Style.StyleID
    override val styleId: Int
        get() = Style.Shuvi

    @Style.StyleName
    override val styleName: String
        get() = Style.ShuviName

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialPurple500

    override val toolbarForegroundColor: Int
        get() = android.R.color.white

}
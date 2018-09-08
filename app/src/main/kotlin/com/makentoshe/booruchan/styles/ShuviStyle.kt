package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R

class ShuviStyle : Style {

    @Style.StyleID
    override val styleId: Int
        get() = Style.Shuvi

    @Style.StyleName
    override val styleName: String
        get() = Style.ShuviName

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialPurple500

    override val toolbarTextColor: Int
        get() = android.R.color.white

}
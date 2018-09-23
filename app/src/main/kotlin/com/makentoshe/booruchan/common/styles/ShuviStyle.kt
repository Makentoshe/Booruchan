package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R

class ShuviStyle : Style {

    override val assentSecondaryColor: Int
        get() = R.color.MaterialPurple200

    override val styleId: Int
        get() = Style.Shuvi

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialPurple500

    override val toolbarForegroundColor: Int
        get() = android.R.color.white

}
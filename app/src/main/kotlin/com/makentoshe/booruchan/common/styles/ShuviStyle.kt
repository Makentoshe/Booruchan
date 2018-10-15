package com.makentoshe.booruchan.common.styles

import android.annotation.SuppressLint
import com.makentoshe.booruchan.R

class ShuviStyle : Style {

    override val toolbar = ToolbarStyle(R.color.MaterialPurple500, android.R.color.white)

    override val view: ViewStyle = ViewStyle(android.R.color.white, android.R.color.black)

    override val floatingActionButton = FloatingActionButtonStyle(R.color.MaterialPurple500, android.R.color.white)

    override val hintColor: Int
        @SuppressLint("PrivateResource")
        get() = R.color.abc_hint_foreground_material_dark

    override val assentSecondaryColor: Int
        get() = R.color.MaterialPurple200

    override val styleId: Int
        get() = Style.Shuvi

}
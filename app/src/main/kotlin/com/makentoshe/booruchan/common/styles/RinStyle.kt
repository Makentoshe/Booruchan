package com.makentoshe.booruchan.common.styles

import android.annotation.SuppressLint
import com.makentoshe.booruchan.R

class RinStyle : Style {

    override val toolbar = ToolbarStyle(R.color.MaterialYellow500, android.R.color.black)

    override val view: ViewStyle = ViewStyle(android.R.color.white, android.R.color.black)

    override val floatingActionButton = FloatingActionButtonStyle(R.color.MaterialYellow500, android.R.color.black)

    override val hintColor: Int
        @SuppressLint("PrivateResource")
        get() = R.color.abc_hint_foreground_material_dark

    override val assentSecondaryColor: Int
        get() = R.color.MaterialYellow200

    override val styleId: Int
        get() = Style.Rin

}
package com.makentoshe.booruchan.common.styles

import android.annotation.SuppressLint
import com.makentoshe.booruchan.R

class RinStyle : Style {

    override val hintColor: Int
        @SuppressLint("PrivateResource")
        get() = R.color.abc_hint_foreground_material_dark

    override val assentSecondaryColor: Int
        get() = R.color.MaterialYellow200

    override val styleId: Int
        get() = Style.Rin

    override val toolbarForegroundColor: Int
        get() = android.R.color.black

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialYellow500

}
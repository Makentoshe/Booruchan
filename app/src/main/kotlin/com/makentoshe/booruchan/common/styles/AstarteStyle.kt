package com.makentoshe.booruchan.common.styles

import android.annotation.SuppressLint
import com.makentoshe.booruchan.R

class AstarteStyle: Style {
    override val hintColor: Int
        @SuppressLint("PrivateResource")
        get() = R.color.abc_hint_foreground_material_dark

    override val assentSecondaryColor: Int
        get() = R.color.MaterialIndigo200

    override val styleId: Int
        get() = Style.Astarte

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialIndigo500

    override val toolbarForegroundColor: Int
        get() = android.R.color.white

}
package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R

class AstarteStyle: Style {

    override val assentSecondaryColor: Int
        get() = R.color.MaterialIndigo200

    override val styleId: Int
        get() = Style.Astarte

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialIndigo500

    override val toolbarForegroundColor: Int
        get() = android.R.color.white

}
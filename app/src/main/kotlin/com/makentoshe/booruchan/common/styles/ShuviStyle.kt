package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R

class ShuviStyle : Style {

    override val avdFromCrossToMagnify: Int
        get() = R.drawable.avd_close_magnify_vector_white

    override val avdFromMagnifyToCross: Int
        get() = R.drawable.avd_magnify_close_vector_white

    override val assentColor: Int
        get() = R.color.PurpleAssent200

    override val styleId: Int
        get() = Style.Shuvi

    override val toolbarBackgroundColor: Int
        get() = R.color.MaterialPurple500

    override val toolbarForegroundColor: Int
        get() = android.R.color.white

}
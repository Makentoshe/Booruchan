package com.makentoshe.booruchan.styles

import android.support.annotation.ColorRes

interface Style {

    @get:ColorRes
    val toolbarTextColor: Int

    @get:ColorRes
    val toolbarBackgroundColor: Int

    val dpToolbarHeight: Int
        get() = 56
}
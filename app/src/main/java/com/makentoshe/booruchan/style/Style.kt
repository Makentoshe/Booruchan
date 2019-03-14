package com.makentoshe.booruchan.style

import androidx.annotation.StyleRes
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R

interface Style {

    @get:StyleRes
    val toolbar: Int

    @get:StyleRes
    val main: Int

    @get:StyleRes
    val progress: Int

}

class SothisStyle : Style {

    override val toolbar: Int
        get() = R.style.SotisToolbar

    override val main: Int
        get() = R.style.Sotis

    override val progress: Int
        get() = R.style.SotisProgress
}


val style: Style = Booruchan.INSTANCE.styleAlt
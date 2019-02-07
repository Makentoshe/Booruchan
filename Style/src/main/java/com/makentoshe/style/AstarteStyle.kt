package com.makentoshe.style

import com.makentoshe.style.element.AssentStyle
import com.makentoshe.style.element.BackgroundStyle
import com.makentoshe.style.element.ChipStyle
import com.makentoshe.style.element.ToolbarStyle
import java.io.Serializable

class AstarteStyle : Style(), Serializable {
    override val id = R.style.Astarte
    override val name = "Astarte"
    override val toolbar =
        ToolbarStyle(R.color.MaterialIndigo500, android.R.color.white, R.color.MaterialIndigo700, android.R.color.white)
    override val assent = AssentStyle(
        R.color.IndigoAssent200, android.R.color.widget_edittext_dark,
        R.color.MaterialIndigo700, android.R.color.white
    )
    override val background = BackgroundStyle(android.R.color.white, android.R.color.black)
    override val chip = ChipStyle(
        R.color.MaterialIndigo700, android.R.color.white,
        R.color.MaterialPink800, android.R.color.white
    )
}
package com.makentoshe.style

import com.makentoshe.style.element.AssentStyle
import com.makentoshe.style.element.BackgroundStyle
import com.makentoshe.style.element.ChipStyle
import com.makentoshe.style.element.ToolbarStyle
import java.io.Serializable

class SotisStyle : Style(), Serializable {
    override val id = R.style.Sotis
    override val name = "Sotis"
    override val toolbar =
        ToolbarStyle(R.color.MaterialTeal600, android.R.color.white)
    override val assent = AssentStyle(
        R.color.TealAssent200, android.R.color.white,
        R.color.MaterialTeal900, android.R.color.white
    )
    override val background = BackgroundStyle(android.R.color.white, android.R.color.black)
    override val chip = ChipStyle(
        R.color.MaterialTeal900, android.R.color.white,
        R.color.MaterialPink800, android.R.color.white
    )
}
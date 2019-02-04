package com.makentoshe.style

import androidx.annotation.StyleRes
import com.makentoshe.style.element.*
import java.io.Serializable

abstract class Style : Serializable {

    abstract val name: String
    @get:StyleRes
    abstract val id: Int

    abstract val toolbar: ToolbarStyle
    abstract val assent: AssentStyle
    abstract val background: BackgroundStyle
    abstract val chip: ChipStyle
    val drawable = DrawableResource()

    companion object {
        @JvmStatic
        fun getStyle(@StyleRes styleRes: Int) = when (styleRes) {
            R.style.Sotis -> SotisStyle()
            R.style.Astarte -> AstarteStyle()
            else -> throw IllegalArgumentException("The param \"$styleRes\" is not one of the declared:\n" +
                    "Sotis - ${R.style.Sotis}\n" +
                    "Astarte - ${R.style.Astarte}")
        }

        @JvmStatic
        fun getStyle(name: String) = when (name) {
            "Sotis" -> SotisStyle()
            "Astarte" -> AstarteStyle()
            else -> throw IllegalArgumentException("The param \"$name\" is not one of the declared:\n" +
                    "Sotis, Astarte")
        }

        @JvmStatic
        val arrayOfStyleNames = arrayOf("Sotis", "Astarte")
    }
}



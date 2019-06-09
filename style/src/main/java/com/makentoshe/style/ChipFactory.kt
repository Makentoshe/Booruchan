package com.makentoshe.style

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.chip.Chip
import org.jetbrains.anko.colorAttr
import org.jetbrains.anko.textColor

class ChipFactory {

    /**
     * @param context received from parent view.
     */
    fun build(context: Context): Chip {
        val primaryColor = context.colorAttr(android.R.attr.background)
        val secondaryColor = context.colorAttr(android.R.attr.textColor)
        //create wrapper application context and Theme.MaterialComponents style
        //this is used for clear view without any styles
        val wrapper = ContextThemeWrapper(context.applicationContext, R.style.AppBaseChip)

        return Chip(wrapper).apply {
            textColor = primaryColor
            chipBackgroundColor = ColorStateList.valueOf(secondaryColor)
            rippleColor = ColorStateList.valueOf(primaryColor)
            closeIconTint = ColorStateList.valueOf(secondaryColor)
            closeIcon = context.getDrawable(com.makentoshe.style.R.drawable.ic_close)!!.apply {
                setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP)
            }
        }
    }
}

package com.makentoshe.booruchan.screen.start.view

import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.ColorInt
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class StartUiToolbarOverflow : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        frameLayout {
            id = R.id.start_toolbar_overflow

            imageView {
                setImageResource(R.drawable.ic_overflow)

                val typedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(android.R.attr.textColor, typedValue, true)
                @ColorInt val color = typedValue.data
                setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }.lparams(dip(24), dip(24)) {
                gravity = Gravity.CENTER
            }
        }.lparams(dip(56), dip(56)) {
            alignParentEnd()
            centerVertically()
        }
    }
}
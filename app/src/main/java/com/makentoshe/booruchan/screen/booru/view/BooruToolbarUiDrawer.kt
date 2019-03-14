package com.makentoshe.booruchan.screen.booru.view

import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

open class BooruToolbarUiDrawer(
    @IdRes private val id: Int
) : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui.owner) {
        frameLayout {
            id = this@BooruToolbarUiDrawer.id
            lparams(dip(56), dip(56))

            imageView {
                setImageResource(R.drawable.ic_menu_vector)
                val typedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(android.R.attr.textColor, typedValue, true)
                @ColorInt val color = typedValue.data
                setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }.lparams(dip(24), dip(24)) {
                gravity = Gravity.CENTER
            }
        }
    }
}
package com.makentoshe.booruchan.screen

import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import org.jetbrains.anko.*

open class ToolbarIcon(
    @IdRes private val id: Int,
    @DrawableRes private val icon: Int,
    private val color: Int
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View =
        with(ui.owner) {
        frameLayout {
            id = this@ToolbarIcon.id
            imageView {
                setImageResource(icon)
                setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }.lparams(dip(24), dip(24)) {
                gravity = Gravity.CENTER
            }
        }.lparams(dip(56), dip(56))
    }

}
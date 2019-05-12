package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.ImageView

abstract class ConcreteTypeController(private val typeView: ImageView) {

    abstract fun setType()

    protected fun setTypeToTypeView(drawable: Drawable) {
        drawable.mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
        typeView.setImageDrawable(drawable)
    }
}
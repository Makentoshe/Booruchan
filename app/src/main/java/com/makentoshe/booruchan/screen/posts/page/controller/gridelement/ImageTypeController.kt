package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.view.View
import android.widget.ImageView

class ImageTypeController(private val typeView: ImageView) : ConcreteTypeController(typeView) {

    override fun setType() {
        typeView.visibility = View.GONE
    }
}
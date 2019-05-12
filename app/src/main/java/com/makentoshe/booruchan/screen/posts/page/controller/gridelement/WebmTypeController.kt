package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.makentoshe.booruchan.R

class WebmTypeController(typeView: ImageView) : ConcreteTypeController(typeView) {

    private val context: Context = typeView.context

    override fun setType() {
        val videoDrawable = context.getDrawable(R.drawable.ic_video)!!
        setTypeToTypeView(videoDrawable)
    }
}
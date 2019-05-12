package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.content.Context
import android.widget.ImageView
import com.makentoshe.booruchan.R

class GifTypeController(typeView: ImageView) : ConcreteTypeController(typeView) {

    private val context: Context = typeView.context

    override fun setType() {
        val animationDrawable = context.getDrawable(R.drawable.ic_animation)!!
        setTypeToTypeView(animationDrawable)
    }
}
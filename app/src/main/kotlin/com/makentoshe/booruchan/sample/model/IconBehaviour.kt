package com.makentoshe.booruchan.sample.model

import android.view.View

abstract class IconBehaviour(private val icon: View? = null) {

    open fun hideIcon() {
        icon?.visibility = View.GONE
    }

    open fun showIcon() {
        icon?.visibility = View.VISIBLE
    }

}
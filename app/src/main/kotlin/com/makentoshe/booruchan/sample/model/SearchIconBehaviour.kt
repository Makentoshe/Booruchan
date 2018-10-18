package com.makentoshe.booruchan.sample.model

import android.view.MenuItem

class SearchIconBehaviour(private val item: MenuItem): IconBehaviour() {

    override fun showIcon() {
        item.isVisible = true
    }

    override fun hideIcon() {
        item.isVisible = false
    }

}
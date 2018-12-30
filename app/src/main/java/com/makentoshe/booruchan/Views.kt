package com.makentoshe.booruchan

import android.widget.ImageView
import android.widget.RelativeLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7._Toolbar
import org.jetbrains.anko.appcompat.v7.toolbar

fun _RelativeLayout.toolbarLayout(
    toolbarInit: (@AnkoViewDslMarker _Toolbar).() -> Unit = {},
    imageViewInit: (@AnkoViewDslMarker ImageView).() -> Unit = {}
) {
    toolbar { toolbarInit() }.lparams(width = matchParent) { alignWithParent = true }
    imageView { imageViewInit() }.lparams(height = dip(24)) {
        setMargins(0, 0, dip(16), 0)
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }
}
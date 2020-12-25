package com.makentoshe.booruchan.view.component

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import org.jetbrains.anko.*

class SeparatorComponent(@ColorRes private val colorRes: Int) : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        view {
            layoutParams = ViewGroup.LayoutParams(matchParent, 1)
            backgroundColorResource = colorRes
        }
    }
}
package com.makentoshe.booruview

import android.content.Context
import android.graphics.Color
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.frameLayout

class BooruPanelFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        frameLayout {
            backgroundColor = Color.MAGENTA
        }
    }
}
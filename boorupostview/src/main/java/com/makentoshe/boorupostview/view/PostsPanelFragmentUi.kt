package com.makentoshe.boorupostview.view

import android.content.Context
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout

class PostsPanelFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        frameLayout {
            id = com.makentoshe.boorupostview.R.id.panelview_content
        }
    }
}
package com.makentoshe.boorusamplesview.view

import android.content.Context
import org.jetbrains.anko.*

class PageFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = ui.relativeLayout {
        // current layout also used as a container for fragments
        id = com.makentoshe.boorusamplesview.R.id.container
        // composite progress bar
        CompositeProgressBar().createView(AnkoContext.createDelegate(this))
    }

}

package com.makentoshe.booruchan.screen.start.view

import com.makentoshe.booruchan.screen.start.StartFragment
import com.makentoshe.booruchan.style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.themedRelativeLayout

class StartFragmentUi : AnkoComponent<StartFragment> {

    override fun createView(ui: AnkoContext<StartFragment>) = with(ui) {
        themedRelativeLayout(style.main) {
            StartUiToolbar().createView(AnkoContext.createDelegate(this))
            StartUiContent().createView(AnkoContext.createDelegate(this))
        }
    }
}


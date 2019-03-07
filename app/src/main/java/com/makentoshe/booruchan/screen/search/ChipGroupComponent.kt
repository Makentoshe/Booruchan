package com.makentoshe.booruchan.screen.search

import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.chipGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

class ChipGroupComponent : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        chipGroup {
            lparams(matchParent, wrapContent)
        }
    }
}
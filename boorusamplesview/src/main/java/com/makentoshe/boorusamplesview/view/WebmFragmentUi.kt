package com.makentoshe.boorusamplesview.view

import android.content.Context
import android.view.ViewManager
import com.google.android.exoplayer2.ui.PlayerView
import com.makentoshe.boorusamplesview.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

class WebmFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = ui.relativeLayout {
        playerview {
            id = R.id.playerview
        }.lparams(matchParent, matchParent)
    }

    private fun ViewManager.playerview(theme: Int = 0, init: PlayerView.() -> Unit): PlayerView {
        return ankoView({ PlayerView(it) }, theme, init)
    }
}
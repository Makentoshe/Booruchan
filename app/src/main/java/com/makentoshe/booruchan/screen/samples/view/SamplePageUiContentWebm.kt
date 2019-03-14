package com.makentoshe.booruchan.screen.samples.view

import android.view.View
import android.view.ViewManager
import com.google.android.exoplayer2.ui.PlayerView
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SamplePageUiContentWebm : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        playerView {
            id = R.id.samples_webm
            visibility = View.GONE
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
    }

    private fun ViewManager.playerView(init: PlayerView.() -> Unit): PlayerView =
        ankoView({ PlayerView(it) }, 0, init)
}
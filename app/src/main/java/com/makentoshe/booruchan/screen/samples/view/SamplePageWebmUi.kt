package com.makentoshe.booruchan.screen.samples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ui.PlayerView
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView

class SamplePageWebmUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        playerView {
            id = R.id.samples_webm
            visibility = View.GONE
        }
    }

    private fun ViewManager.playerView(init: PlayerView.() -> Unit): PlayerView =
        ankoView({ PlayerView(it) }, 0, init)
}
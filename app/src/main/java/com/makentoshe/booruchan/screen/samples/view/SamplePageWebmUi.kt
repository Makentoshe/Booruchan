package com.makentoshe.booruchan.screen.samples.view

import android.view.Gravity
import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ui.PlayerView
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

class SamplePageWebmUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) { gravity = Gravity.CENTER }

            playerView {
                id = R.id.samples_webm
                visibility = View.GONE
            }.lparams(matchParent, matchParent) {
                gravity = Gravity.CENTER
            }
        }
    }

    private fun ViewManager.playerView(init: PlayerView.() -> Unit): PlayerView {
        return ankoView({ PlayerView(it) }, 0, init)
    }
}
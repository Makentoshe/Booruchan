package com.makentoshe.booruchan.screen.webmplayer

import android.view.Gravity
import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ui.PlayerView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class WebmPlayerUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View =
        with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) { gravity =
                Gravity.CENTER
            }

            themedFrameLayout(style.default).lparams(matchParent, matchParent) {
                gravity = Gravity.CENTER
            }

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
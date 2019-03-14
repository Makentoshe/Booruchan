package com.makentoshe.booruchan.screen.samples.view

import android.view.View
import android.view.ViewManager
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import pl.droidsonroids.gif.GifImageView

class SamplePageUiContentGif : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        gifImageView {
            id = R.id.samples_gif
            visibility = View.GONE
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
    }

    private fun ViewManager.gifImageView(init: GifImageView.() -> Unit): GifImageView =
        ankoView({ GifImageView(it) }, 0, init)
}
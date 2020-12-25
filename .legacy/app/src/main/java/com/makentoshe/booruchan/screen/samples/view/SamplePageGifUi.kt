package com.makentoshe.booruchan.screen.samples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import pl.droidsonroids.gif.GifImageView

class SamplePageGifUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        gifImageView {
            id = R.id.samples_gif
            visibility = View.GONE
        }
    }

    private fun ViewManager.gifImageView(init: GifImageView.() -> Unit): GifImageView =
        ankoView({ GifImageView(it) }, 0, init)
}
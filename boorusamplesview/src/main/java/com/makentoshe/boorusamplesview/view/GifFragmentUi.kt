package com.makentoshe.boorusamplesview.view

import android.content.Context
import android.view.View
import android.view.ViewManager
import com.makentoshe.boorusamplesview.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout
import pl.droidsonroids.gif.GifImageView

class GifFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = ui.relativeLayout {
        // progress bar
        CompositeProgressBar().createView(AnkoContext.createDelegate(this))
        // content
        gifImageView {
            id = R.id.gifview
            visibility = View.GONE
        }.lparams(matchParent, matchParent)
    }

    private fun ViewManager.gifImageView(theme: Int = 0, init: GifImageView.() -> Unit): GifImageView {
        return ankoView({ GifImageView(it) }, theme, init)
    }
}
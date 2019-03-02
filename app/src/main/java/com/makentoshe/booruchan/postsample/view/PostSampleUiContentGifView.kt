package com.makentoshe.booruchan.postsample.view

import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.matchParent
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

class PostSampleUiContentGifView(private val byteArray: ByteArray) : AnkoComponent<_FrameLayout> {
    override fun createView(ui: AnkoContext<_FrameLayout>): View = with(ui.owner) {
        val gifDrawable = GifDrawable(byteArray)
        gifImageView {
            setImageDrawable(gifDrawable)
            gifDrawable.start()
        }.lparams(matchParent, matchParent)
    }

    private fun ViewManager.gifImageView(init: GifImageView.() -> Unit): GifImageView {
        return ankoView({ GifImageView(it) }, 0, init)
    }
}
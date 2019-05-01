package com.makentoshe.booruchan.screen.samples.controller

import android.view.View
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.GifDownloadListener
import org.jetbrains.anko.find
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

/**
 * Controller for [SamplePageGifFragment].
 * Displays a gif animation after a successfull downloading.
 */
class SamplePageGifController(listener: GifDownloadListener) : SamplePageController<GifDrawable>(listener) {

    override fun bindOnSuccess(view: View, t: GifDrawable) {
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        //hide preview image
        view.find<ImageView>(R.id.samples_preview).visibility =
            View.GONE
        //setup gif view
        view.find<GifImageView>(R.id.samples_gif).apply {
            visibility = View.VISIBLE
            setImageDrawable(t)
        }
        //start gif animation
        t.start()
    }
}
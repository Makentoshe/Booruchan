package com.makentoshe.booruchan.postsample.view

import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewManager
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsample.PostSampleViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import java.lang.Exception

class PostSampleUiContent(
    private val viewModel: PostSampleViewModel,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        frameLayout {
            id = R.id.postsample_content
            visibility = View.GONE

            viewModel.onSampleDownloadedListener { onSampleDownloaded(it) }

        }.lparams(matchParent, matchParent)
    }

    /* Tries to define "image" type and setups it to correct view. */
    private fun _FrameLayout.onSampleDownloaded(byteArray: ByteArray) {
        //check the array is a gif or an image
        if (processAsGif(byteArray)) {
            visibility = View.VISIBLE
            return
        }
        if (processAsImage(byteArray)) {
            visibility = View.VISIBLE
            return
        }
        return
    }

    /* Try to make from this byte array a gif image an set it to the GifImageView
     * Returns true if the byte array is a gif format and false otherwise. */
    private fun _FrameLayout.processAsGif(byteArray: ByteArray): Boolean {
        try {
            val gifDrawable = GifDrawable(byteArray)
            gifImageView {
                setImageDrawable(gifDrawable)
            }.lparams(matchParent, matchParent)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    /* Try to make from bytes a simple image and set it to the SubsampleingScaleImageView
    * Returns true if the byte array is an any image format and false otherwise */
    private fun _FrameLayout.processAsImage(byteArray: ByteArray): Boolean {
        try {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            subsamplingScaleImageView {
                setImage(ImageSource.bitmap(bitmap))
            }.lparams(matchParent, matchParent)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun ViewManager.subsamplingScaleImageView(init: SubsamplingScaleImageView.() -> Unit): SubsamplingScaleImageView {
        return ankoView({ SubsamplingScaleImageView(it) }, 0, init)
    }

    private fun ViewManager.gifImageView(init: GifImageView.() -> Unit): GifImageView {
        return ankoView({ GifImageView(it) }, 0, init)
    }
}
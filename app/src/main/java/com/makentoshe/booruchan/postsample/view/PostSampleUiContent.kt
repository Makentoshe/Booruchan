package com.makentoshe.booruchan.postsample.view

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.view.ViewManager
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsample.PostSampleViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

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
        //is byte array represents a gif animation
        if (processAsGif(byteArray)) {
            visibility = View.VISIBLE
            return
        }
        //is byte array represents an image
        if (processAsImage(byteArray)) {
            visibility = View.VISIBLE
            return
        }
        if (processAsWebm(byteArray)) {
            visibility = View.VISIBLE
            return
        }
        viewModel.pushException(RuntimeException("This file format does not supports now"))
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

    /* Try to make a Uri from byte array and create a webm player and play a video*/
    private fun _FrameLayout.processAsWebm(byteArray: ByteArray): Boolean {
        try {
            playerView {
                backgroundColorResource = style.background.backgroundColorRes
                player = initPlayer(byteArray)

            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    /* Inits an ExoPlayer for displaying a webm file.*/
    private fun PlayerView.initPlayer(byteArray: ByteArray): ExoPlayer {
        val userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))
        val dataSourceFactory = DefaultDataSourceFactory(context, userAgent)
        val uri = Uri.parse(String(byteArray))
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        return ExoPlayerFactory.newSimpleInstance(context).apply { prepare(mediaSource) }
    }

    private fun ViewManager.subsamplingScaleImageView(init: SubsamplingScaleImageView.() -> Unit): SubsamplingScaleImageView {
        return ankoView({ SubsamplingScaleImageView(it) }, 0, init)
    }

    private fun ViewManager.gifImageView(init: GifImageView.() -> Unit): GifImageView {
        return ankoView({ GifImageView(it) }, 0, init)
    }

    private fun ViewManager.playerView(init: PlayerView.() -> Unit): PlayerView {
        return ankoView({ PlayerView(it) }, 0, init)
    }
}
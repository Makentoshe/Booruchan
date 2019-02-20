package com.makentoshe.booruchan.postsample.view

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.view.ViewManager
import android.webkit.URLUtil
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsample.model.DownloadErrorController
import com.makentoshe.booruchan.postsample.model.SampleImageDownloadController
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

class PostSampleUiContent(
    private val downloadErrorController: DownloadErrorController,
    private val sampleDownloadController: SampleImageDownloadController,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        frameLayout {
            id = R.id.postsample_content
            visibility = View.GONE

            sampleDownloadController.onSampleLoadingFinished {
                visibility = View.VISIBLE
            }

            sampleDownloadController.onSampleGifLoaded {
                //is byte array represents a gif animation
                if (processAsGif(it)) return@onSampleGifLoaded
                downloadErrorController.push(Exception("This file format does not supports now"))
            }

            sampleDownloadController.onSampleWebmUrlLoaded {
                //is byte array represents a video file
                if (processAsWebm(it)) return@onSampleWebmUrlLoaded
                downloadErrorController.push(Exception("This file format does not supports now"))
            }

            sampleDownloadController.onSampleImageLoaded {
                //is byte array represents an image
                if (processAsImage(it)) return@onSampleImageLoaded
                downloadErrorController.push(Exception("This file format does not supports now"))
            }

            sampleDownloadController.onSampleImageLoadingError {
                visibility = View.GONE
            }

        }.lparams(matchParent, matchParent)
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
    private fun _FrameLayout.processAsWebm(url: String): Boolean {
        if (!URLUtil.isValidUrl(url)) return false
        try {
            playerView {
                backgroundColorResource = style.background.backgroundColorRes
                player = initPlayer(url)
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    /* Inits an ExoPlayer for displaying a webm file.*/
    private fun PlayerView.initPlayer(url: String): ExoPlayer {
        val userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))
        val dataSourceFactory = DefaultDataSourceFactory(context, userAgent)
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url))
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
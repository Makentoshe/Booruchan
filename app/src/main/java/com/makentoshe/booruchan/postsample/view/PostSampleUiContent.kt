package com.makentoshe.booruchan.postsample.view

import android.view.View
import android.webkit.URLUtil
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsample.model.DownloadErrorController
import com.makentoshe.booruchan.postsample.model.SampleImageDownloadController
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostSampleUiContent(
    private val downloadErrorController: DownloadErrorController,
    private val sampleDownloadController: SampleImageDownloadController
) : AnkoComponent<_RelativeLayout> {

    private lateinit var contentview: _FrameLayout

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        frameLayout {
            contentview = this
            id = R.id.postsample_content
            visibility = View.GONE

            sampleDownloadController.onSampleLoadingFinished(::onSampleLoadingFinished)
            sampleDownloadController.onSampleGifLoaded(::onSampleGifLoaded)
            sampleDownloadController.onSampleWebmUrlLoaded(::onSampleWebmLoaded)
            sampleDownloadController.onSampleImageLoaded(::onSampleImageLoaded)
            sampleDownloadController.onSampleImageLoadingError(::onSampleLoadingError)

        }.lparams(matchParent, matchParent)
    }

    private fun onSampleImageLoaded(byteArray: ByteArray) {
        try {
            PostSampleUiContentImageView(byteArray).createView(AnkoContext.createDelegate(contentview))
        } catch (e: Exception) {
            downloadErrorController.push(e)
        }
    }

    private fun onSampleGifLoaded(byteArray: ByteArray) {
        try {
            PostSampleUiContentGifView(byteArray).createView(AnkoContext.createDelegate(contentview))
        } catch (e: Exception) {
            downloadErrorController.push(e)
        }
    }

    private fun onSampleWebmLoaded(url: String) {
        try {
            PostSampleUiContentWebmView(url).createView(AnkoContext.createDelegate(contentview))
        } catch (e: Exception) {
            downloadErrorController.push(e)
        }
    }

    private fun onSampleLoadingFinished() {
        contentview.visibility = View.VISIBLE
    }

    private fun onSampleLoadingError(e: Exception) {
        contentview.visibility = View.GONE
    }
}
package com.makentoshe.booruchan.postsample.view

import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewManager
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsample.PostSampleViewModel
import com.makentoshe.booruchan.postsample.SampleFormat
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class PostSampleUiContent(
    private val viewModel: PostSampleViewModel,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        frameLayout {
            id = R.id.postsample_content
            visibility = View.GONE
            viewModel.onSampleDownloadedListener { format, bytes ->
                onSampleDownloaded(format, bytes)
            }
        }.lparams(matchParent, matchParent)
    }

    private fun _FrameLayout.onSampleDownloaded(format: SampleFormat, byteArray: ByteArray) {
        visibility = View.VISIBLE
        when (format) {
            SampleFormat.IMAGE -> onImageReceived(byteArray)
            SampleFormat.GIF -> onImageReceived(byteArray)
            SampleFormat.WEBM -> Unit
        }
    }

    private fun _FrameLayout.onImageReceived(byteArray: ByteArray) {
        subsamplingScaleImageView {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            setImage(ImageSource.bitmap(bitmap))
        }.lparams(matchParent, matchParent)
    }

    private fun ViewManager.subsamplingScaleImageView(init: SubsamplingScaleImageView.() -> Unit): SubsamplingScaleImageView {
        return ankoView({ SubsamplingScaleImageView(it) }, 0, init)
    }
}
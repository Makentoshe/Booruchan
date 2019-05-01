package com.makentoshe.booruchan.screen.samples.controller

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadListener
import org.jetbrains.anko.find

class SamplePageImageController(listener: ImageDownloadListener) : SamplePageController<Bitmap>(listener) {

    protected override fun bindOnSuccess(view: View, t: Bitmap) {
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        //hide preview image
        view.find<ImageView>(R.id.samples_preview).visibility =
            View.GONE
        //setup full image
        view.find<SubsamplingScaleImageView>(R.id.samples_image).apply {
            visibility = View.VISIBLE
            setImage(ImageSource.bitmap(t))
        }
    }
}
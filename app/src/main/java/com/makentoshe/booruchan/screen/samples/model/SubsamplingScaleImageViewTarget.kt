package com.makentoshe.booruchan.screen.samples.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView

class SubsamplingScaleImageViewTarget(
    view: SubsamplingScaleImageView
) : CustomViewTarget<SubsamplingScaleImageView, Bitmap>(view) {
    override fun onLoadFailed(errorDrawable: Drawable?) {
        val bitmap = errorDrawable?.toBitmap() ?: return
        view.setImage(ImageSource.bitmap(bitmap))
    }

    override fun onResourceCleared(placeholder: Drawable?) {
        val bitmap = placeholder?.toBitmap() ?: return
        view.setImage(ImageSource.bitmap(bitmap))
    }

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
        view.setImage(ImageSource.bitmap(resource))
    }
}
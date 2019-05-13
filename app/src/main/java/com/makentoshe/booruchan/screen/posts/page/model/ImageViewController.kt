package com.makentoshe.booruchan.screen.posts.page.model

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import com.makentoshe.booruchan.R

class ImageViewController(private val imageView: ImageView) {

    fun onSuccess(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

    fun onError(throwable: Throwable) {
        val drawable = imageView.context.getDrawable(R.drawable.ic_alert_octagon_outline)!!
        drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        imageView.setImageDrawable(drawable)
    }

}
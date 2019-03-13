package com.makentoshe.booruchan.screen.samples.model

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Post
import org.jetbrains.anko.find
import java.io.File

class SamplePageFragmentCompleteConsumer(
    private val post: Post
) : SamplePageFragmentErrorConsumer() {

    override fun accept(t: View) {
        when (File(post.sampleUrl).extension) {
            "webm" -> Unit
            "gif" -> { }
            else -> onCompleteImage(t, post)
        }
    }

    private fun onCompleteImage(view: View, post: Post) {
        val imageview = view.find<SubsamplingScaleImageView>(R.id.samples_image)

        val request = Glide.with(view).asBitmap()
            .load(post.sampleUrl)
            .listener(object : SimpleRequestListener<Bitmap>() {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Handler(Looper.getMainLooper()).post {
                        onError(view, e ?: RuntimeException("Something goes wrong"))
                    }
                    return super.onLoadFailed(e, model, target, isFirstResource)
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Handler(Looper.getMainLooper()).post {
                        imageview.visibility = View.VISIBLE
                    }
                    return super.onResourceReady(resource, model, target, dataSource, isFirstResource)
                }
            })

        Handler(Looper.getMainLooper()).post {
            request.into(SubsamplingScaleImageViewTarget(imageview))
        }
    }

    open class SimpleRequestListener<T> : RequestListener<T> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<T>?, isFirstResource: Boolean) = false
        override fun onResourceReady(
            resource: T,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ) = false
    }
}
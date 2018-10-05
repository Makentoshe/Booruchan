package com.makentoshe.booruchan.booru.model.content.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.common.api.entity.Post
import kotlinx.coroutines.experimental.Job

open class PreviewLoader(private val downloader: Downloader) {

    fun getPostPreview(post: Post, action: (Bitmap?) -> Unit): Job {
        return downloader.download(post.previewUrl) {
            action(BitmapFactory.decodeStream(it))
        }
    }

}
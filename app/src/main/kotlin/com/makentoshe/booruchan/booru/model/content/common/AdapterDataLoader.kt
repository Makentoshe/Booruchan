package com.makentoshe.booruchan.booru.model.content.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.Posts
import com.makentoshe.booruchan.common.api.entity.Post
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import kotlin.Exception

class AdapterDataLoader(val searchTerm: String,
                        private val downloader: Downloader,
                        private val booru: Boor) {

    private val postDataLoadScheduler = JobScheduler(10)

    fun getPostsData(position: Int, action: (Posts<out Post>?) -> Unit) {
        val job = GlobalScope.launch(Dispatchers.Default) {
            var errWasNotShown = true
            do {
                try {
                    booru.getPostsByTags(3, searchTerm, position, downloader.client, action)
                    break
                } catch (e: Exception) {
                    if (errWasNotShown) {
                        errWasNotShown = false
                        action(null)
                    }
                    Thread.yield()
                }
            } while (true)
        }
        postDataLoadScheduler.addJob(job)
    }

    fun getPostPreview(post: Post, action: (Bitmap?) -> Unit) {
        downloader.download(post.previewUrl) {
            action(BitmapFactory.decodeStream(it))
        }
    }

    fun clearScheduler() {
        postDataLoadScheduler.jobDeque.forEach {
            it.cancel()
        }
        postDataLoadScheduler.jobDeque.clear()
    }

}
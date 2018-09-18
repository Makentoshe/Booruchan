package com.makentoshe.booruchan.booru.model.gallery.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.booru.model.gallery.common.Downloader
import com.makentoshe.booruchan.booru.model.gallery.common.JobScheduler
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.Posts
import com.makentoshe.booruchan.common.api.entity.Post
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class AdapterDataLoader(val searchTerm: String,
                        private val downloader: Downloader,
                        private val booru: Boor) {

    private val postDataLoadScheduler = JobScheduler(10)

    fun getPostsData(position: Int, action: (Posts<out Post>) -> Unit) {
        val job = GlobalScope.launch(Dispatchers.Default) {
            booru.getPostsByTags(3, searchTerm, position, downloader.client) {
                action(it)
            }
        }
        postDataLoadScheduler.addJob(job)
    }

    fun getPostPreview(post: Post, action: (Bitmap) -> Unit) {
        downloader.download(post.previewUrl) {
            action(BitmapFactory.decodeStream(it))
        }
    }

}
package com.makentoshe.booruchan.booru.model.content.comments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.booru.model.content.common.DataLoader
import com.makentoshe.booruchan.booru.model.content.common.Downloader
import com.makentoshe.booruchan.booru.model.content.common.JobScheduler
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.entity.Post
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class CommentsContentDataLoader(private val downloader: Downloader,
                                private val booru: Boor) : DataLoader() {

    private val postLoadingScheduler = JobScheduler(5)
    private val postPreviewLoadingScheduler = JobScheduler(5)

    fun getPostIds(page: Int, action: (IntArray) -> (Unit)) {
        GlobalScope.launch {
            booru.getListOfLastCommentedPostIds(page * 10, downloader.client, action)
        }
    }

    fun getPostById(id: Int, action: (Post) -> Unit) {
        val job = GlobalScope.launch {
            booru.getPostById(id, downloader.client, action)
        }
        postLoadingScheduler.addJob(job)
    }

    fun getPostPreview(post: Post, action: (Bitmap) -> Unit) {
        val job = downloader.download(post.previewUrl) {
            action(BitmapFactory.decodeStream(it!!))
        }
        postPreviewLoadingScheduler.addJob(job)
    }

}
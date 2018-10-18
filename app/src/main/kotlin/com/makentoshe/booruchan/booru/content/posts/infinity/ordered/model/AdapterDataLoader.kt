package com.makentoshe.booruchan.booru.content.posts.infinity.ordered.model

import com.makentoshe.booruchan.booru.content.model.Downloader
import com.makentoshe.booruchan.booru.content.model.JobScheduler
import com.makentoshe.booruchan.booru.content.model.PreviewLoader
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.Posts
import com.makentoshe.booruchan.common.api.entity.Post
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class AdapterDataLoader(val searchTerm: String = "", downloader: Downloader,
                        private val booru: Boor) : PreviewLoader(downloader) {

    private val postDataLoadScheduler = JobScheduler(10)

    fun getPostsData(position: Int, action: (Posts<out Post>?) -> Unit) {
        val job = GlobalScope.launch(Dispatchers.Default) {
            var errWasNotShown = true
            do {
                try {
                    action(booru.getPostsByTags(3, searchTerm, position))
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

    fun getPostsData2(position: Int, action: (Posts<out Post>) -> Unit) {
        val job = GlobalScope.launch(Dispatchers.Default) {
            delay(100)
            action(booru.getPostsByTags(3, searchTerm, position))
        }
        postDataLoadScheduler.addJob(job)
    }

    fun clearScheduler() {
        postDataLoadScheduler.jobDeque.forEach {
            it.cancel()
        }
        postDataLoadScheduler.jobDeque.clear()
    }

}
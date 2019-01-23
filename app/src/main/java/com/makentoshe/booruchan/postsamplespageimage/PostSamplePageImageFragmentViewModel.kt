package com.makentoshe.booruchan.postsamplespageimage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.ImageDownloadController
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.repository.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostSamplePagePreviewFragmentViewModel(
    samplesRepository: ImageRepository,
    val position: Int,
    private val postsRepository: PostsRepository
) : ViewModel(), CoroutineScope {

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val sampleDownloadController = ImageDownloadController(this, samplesRepository)

    /**
     * @param position post index start from 0.
     */
    fun getPost(position: Int): Post {
        return runBlocking {
            val posts = withContext(Dispatchers.Default) {
                postsRepository.get(position / postsRepository.count)
            }
            posts[position % postsRepository.count]
        }
    }

    fun subscribe(action: (Bitmap?) -> Unit) {
        sampleDownloadController.subscribe(getPost(position), action)
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

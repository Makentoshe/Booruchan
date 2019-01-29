package com.makentoshe.booruchan.postsamplespageimage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.ImageDownloadController
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostsRepository
import kotlinx.coroutines.*
import java.lang.Exception
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
    fun getPost(position: Int): Deferred<DownloadResult<Post>> = async {
        try {
            val post: Post = withContext(Dispatchers.Default) {
                postsRepository.get(position / postsRepository.count)[position % postsRepository.count]
            }
            return@async DownloadResult(post)
        } catch (e: Exception) {
            return@async DownloadResult<Post>(exception = e)
        }
    }

    fun subscribe(action: (DownloadResult<Bitmap>) -> Unit) = launch {
        val result = getPost(position).await()
        sampleDownloadController.subscribe(result, action)
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

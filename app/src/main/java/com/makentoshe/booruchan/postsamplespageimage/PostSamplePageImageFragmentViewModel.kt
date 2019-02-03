package com.makentoshe.booruchan.postsamplespageimage

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postsamples.model.SamplePageHorizontalScrollBlockController
import com.makentoshe.booruchan.postsamplespageimage.model.SampleImageDownloadController
import kotlinx.coroutines.*
import java.lang.Exception

class PostSamplePageImageFragmentViewModel(
    val position: Int,
    private val postsRepository: PostsRepository,
    samplesRepository: ImageRepository
) : FragmentViewModel() {

    private val sampleImageDownloadController = SampleImageDownloadController(this, samplesRepository)


    init {
        startSampleImageLoading()
    }

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

    fun onFinishSampleImageLoadingListener(action: (DownloadResult<Bitmap>) -> Unit) {
        sampleImageDownloadController.subscribe {
            Handler(Looper.getMainLooper()).post { action(it) }
        }
    }

    fun startSampleImageLoading() = launch {
        sampleImageDownloadController.action(getPost(position).await())
    }

    override fun onUiRecreate() {
        sampleImageDownloadController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        sampleImageDownloadController.clear()
    }
}


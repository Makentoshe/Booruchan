package com.makentoshe.booruchan.postsamplespageimage

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.FragmentViewModel
import com.makentoshe.booruchan.postsamplespageimage.model.SampleImageDownloadController
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel
import kotlinx.coroutines.*

class PostSamplePageImageFragmentViewModel private constructor() : ViewModel() {

    private var position: Int = -1
    private lateinit var postsRepository: PostsRepository
    private lateinit var sampleImageDownloadController: SampleImageDownloadController

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

    override fun onCreateView(owner: Fragment) {
        sampleImageDownloadController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        sampleImageDownloadController.clear()
    }

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val samplesRepository: ImageRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplePageImageFragmentViewModel()
            viewModel.position = position
            viewModel.sampleImageDownloadController = SampleImageDownloadController(viewModel, samplesRepository)
            viewModel.postsRepository = postsRepository
            return viewModel as T
        }
    }
}


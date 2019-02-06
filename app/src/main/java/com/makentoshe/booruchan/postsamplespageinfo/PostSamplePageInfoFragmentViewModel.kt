package com.makentoshe.booruchan.postsamplespageinfo

import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PostSamplePageInfoFragmentViewModel : ViewModel() {
    private var position: Int = -1
    private lateinit var postsRepository: PostsRepository

    /**
     * @param position post index start from 0.
     */
    fun getPost(): Deferred<DownloadResult<Post>> = async {
        try {
            val post: Post =
                withContext(Dispatchers.Default) {
                    postsRepository.get(position / postsRepository.count)[position % postsRepository.count]
                }
            return@async DownloadResult(post)
        } catch (e: Exception) {
            return@async DownloadResult<Post>(exception = e)
        }
    }

    fun getRecyclerAdapter() = RecyclerViewAdapter(this)

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplePageInfoFragmentViewModel()
            viewModel.position = position
            viewModel.postsRepository = postsRepository
            return viewModel as T
        }
    }
}
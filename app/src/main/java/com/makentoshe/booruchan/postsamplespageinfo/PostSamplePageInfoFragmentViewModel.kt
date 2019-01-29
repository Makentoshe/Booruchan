package com.makentoshe.booruchan.postsamplespageinfo

import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.posts.model.PostsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostSamplePageInfoFragmentViewModel(
    private val position: Int,
    private val postsRepository: PostsRepository
) : ViewModel(), CoroutineScope {

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

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



    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    fun getRecyclerAdapter() = RecyclerViewAdapter(this)
}
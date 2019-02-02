package com.makentoshe.booruchan.postsamplespageinfo

import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.FragmentViewModel
import com.makentoshe.booruchan.PostsRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PostSamplePageInfoFragmentViewModel(
    private val position: Int,
    private val postsRepository: PostsRepository
) : FragmentViewModel() {

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
}
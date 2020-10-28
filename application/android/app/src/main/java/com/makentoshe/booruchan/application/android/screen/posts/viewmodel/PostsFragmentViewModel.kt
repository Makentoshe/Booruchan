package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.network.PostsRequest
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsFilter
import com.makentoshe.booruchan.core.post.network.PostsNetworkManager
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class PostsFragmentViewModel(
    private val booruContext: BooruContext,
    private val networkManager: PostsNetworkManager<PostsRequest>
) : ViewModel() {

    val title = booruContext.title

    init {
        val postsContext = booruContext.posts { networkManager.getPosts(it) } as PostsContext<PostsRequest, PostsFilter>
        val postsRequest = postsContext.buildRequest(GelbooruPostsFilter())

        println(postsContext)
        println(postsRequest)

        thread {
            runBlocking {
                val result = postsContext.get(postsRequest)
                println(result)
            }
        }
    }

    class Factory(
        private val booruContext: BooruContext,
        private val networkManager: PostsNetworkManager<PostsRequest>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsFragmentViewModel(booruContext, networkManager) as T
        }
    }
}


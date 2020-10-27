package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import context.BooruContext
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import post.context.PostsContext
import post.network.GelbooruPostsFilter
import post.network.PostsFilter
import post.network.PostsNetworkManager
import post.network.PostsRequest
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

class PostsNetworkManager(
    private val client: HttpClient
) : PostsNetworkManager<PostsRequest> {

    private suspend fun internalPosts(request: PostsRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getPosts(request: PostsRequest): Result<String> = try {
        Result.success(internalPosts(request).receive())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

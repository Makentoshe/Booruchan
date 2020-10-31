package com.makentoshe.booruchan.application.android.screen.posts.model

import androidx.paging.PageKeyedDataSource
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/** Fetches posts from [postsArena] with query based on [filterBuilder] in the [coroutineScope] context */
class PostsDataSource(
    private val postsArena: Arena<PostsFilter, PostsDeserialize<Post>>,
    private val filterBuilder: PostsFilter.Builder,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, Result<PostDeserialize<Post>>>() {

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result<PostDeserialize<Post>>>) {
        coroutineScope.launch {
            println("Before ${this.coroutineContext} key=${params.key} size${params.requestedLoadSize}")
            val result = postsArena.suspendFetch(filterBuilder.build(params.requestedLoadSize, params.key))
            val success = result.getOrNull() ?: throw result.exceptionOrNull()!!
            callback.onResult(success.deserializes, params.key - 1)
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result<PostDeserialize<Post>>>) {
        coroutineScope.launch {
            println("Initial ${this.coroutineContext} ${params.requestedLoadSize}")
            val result = postsArena.suspendFetch(filterBuilder.build(params.requestedLoadSize, 0))
            val success = result.getOrNull() ?: throw result.exceptionOrNull()!!
            callback.onResult(success.deserializes, null, 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result<PostDeserialize<Post>>>) {
        coroutineScope.launch {
            println("After ${this.coroutineContext} key=${params.key} size${params.requestedLoadSize}")
            val result = postsArena.suspendFetch(filterBuilder.build(params.requestedLoadSize, params.key))
            val success = result.getOrNull() ?: throw result.exceptionOrNull()!!
            callback.onResult(success.deserializes, params.key + 1)
        }
    }
}


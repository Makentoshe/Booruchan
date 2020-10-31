package com.makentoshe.booruchan.application.android.screen.posts.model

import androidx.paging.PageKeyedDataSource
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PostsDataSource(
    private val postsArena: PostsArena,
    private val filterBuilder: PostsFilter.Builder,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, Result<PostDeserialize<Post>>>() {

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result<PostDeserialize<Post>>>) {
        println("Before ${Thread.currentThread()}")
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
        println("After ${Thread.currentThread()}")
    }

}


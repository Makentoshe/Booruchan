package com.makentoshe.booruchan.application.android.screen.posts.model

import androidx.paging.PageKeyedDataSource
import com.makentoshe.booruchan.application.core.Arena
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/** Fetches posts from [postsArena] with query based on [filterBuilder] in the [coroutineScope] context */
class PostsDataSource(
    private val postsArena: Arena<PostsFilter, PostsDeserialize<Post>>,
    private val filterBuilder: PostsFilter.Builder,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, Result<PostDeserialize<Post>>>() {

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result<PostDeserialize<Post>>>) {
        coroutineScope.launch(Dispatchers.IO) {
            println("Before isActive=${isActive} ${Thread.currentThread()} key=${params.key} size${params.requestedLoadSize}")
            val result = postsArena.suspendFetch(filterBuilder.build(params.requestedLoadSize, params.key))
            val success = result.getOrNull() ?: throw result.exceptionOrNull()!!
            callback.onResult(success.deserializes, params.key - 1)
        }
    }

    // Indicates that the initial load was finished
    val initialSignal: Subject<Result<*>> = BehaviorSubject.create()
    // Stores last [loadInitial] arguments for retrying
    private lateinit var lastInitialSnapshot: LastInitialSnapshot

    fun retryLoadInitial() {
        loadInitial(lastInitialSnapshot.params, lastInitialSnapshot.callback)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result<PostDeserialize<Post>>>
    ) {
        lastInitialSnapshot = LastInitialSnapshot(params, callback)
        coroutineScope.launch(Dispatchers.IO) {
            println("Initial isActive=${isActive} ${Thread.currentThread()} ${params.requestedLoadSize}")
            suspendLoadInitial(params, callback)
        }
    }

    private suspend fun suspendLoadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result<PostDeserialize<Post>>>
    ) {
        val result = postsArena.suspendFetch(filterBuilder.build(params.requestedLoadSize, 0))
        // if null - call signal with exception result and finish execution
        val success = result.getOrNull() ?: return initialSignal.onNext(result)
        callback.onResult(success.deserializes, null, 1)
        initialSignal.onNext(result)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result<PostDeserialize<Post>>>) {
        coroutineScope.launch(Dispatchers.IO) {
            println("After isActive=$isActive ${Thread.currentThread()} key=${params.key} size${params.requestedLoadSize}")
            val result = postsArena.suspendFetch(filterBuilder.build(params.requestedLoadSize, params.key))
            val success = result.getOrNull() ?: throw result.exceptionOrNull()!!
            callback.onResult(success.deserializes, params.key + 1)
        }
    }

    internal data class LastInitialSnapshot(
        val params: LoadInitialParams<Int>,
        val callback: LoadInitialCallback<Int, Result<PostDeserialize<Post>>>
    )
}


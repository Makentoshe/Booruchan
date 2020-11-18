package com.makentoshe.booruchan.application.android.screen.posts.model

import androidx.paging.PageKeyedDataSource
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.network.filter.TagsFilterEntry
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
    private val coroutineScope: CoroutineScope,
    private val tagsFilter: TagsFilterEntry
) : PageKeyedDataSource<Int, Result<PostDeserialize<Post>>>(), PostsDataSourceAfter {

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result<PostDeserialize<Post>>>) {
        coroutineScope.launch(Dispatchers.IO) {
            println("Before isActive=${isActive} ${Thread.currentThread()} key=${params.key} size${params.requestedLoadSize}")
            val countFilter = filterBuilder.count.build(params.requestedLoadSize)
            val pageFilter = filterBuilder.page.build(params.key)
            val result = postsArena.suspendFetch(filterBuilder.build(countFilter, pageFilter, tagsFilter))
            val success = result.getOrNull() ?: throw result.exceptionOrNull()!!
            callback.onResult(success.deserializes, params.key - 1)
        }
    }

    // Indicates that the initial load was finished
    val initialSignal: Subject<Result<*>> = BehaviorSubject.create()

    // Stores last [loadInitial] arguments for retrying
    private lateinit var lastInitialSnapshot: LastInitialSnapshot

    /** Reruns last [loadInitial] */
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
        val countFilter = filterBuilder.count.build(params.requestedLoadSize)
        val pageFilter = filterBuilder.page.build(0)
        val result = postsArena.suspendFetch(filterBuilder.build(countFilter, pageFilter, tagsFilter))
        // if null - call signal with exception result and finish execution
        val success = result.getOrNull() ?: return initialSignal.onNext(result)
        callback.onResult(success.deserializes, null, 3)
        initialSignal.onNext(result)
    }

    // Indicates that the after load was finished
    override val afterSignal: BehaviorSubject<Result<*>> = BehaviorSubject.create()

    // Stores last [loadAfter] arguments for retrying
    private lateinit var lastAfterSnapshot: LastAfterSnapshot

    /** Reruns last [loadAfter] */
    override fun retryLoadAfter() {
        loadAfter(lastAfterSnapshot.params, lastAfterSnapshot.callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result<PostDeserialize<Post>>>) {
        lastAfterSnapshot = LastAfterSnapshot(params, callback)
        coroutineScope.launch(Dispatchers.IO) {
            println("After isActive=$isActive ${Thread.currentThread()} key=${params.key} size${params.requestedLoadSize}")
            suspendLoadAfter(params, callback)
        }
    }

    private suspend fun suspendLoadAfter(
        params: LoadParams<Int>, callback: LoadCallback<Int, Result<PostDeserialize<Post>>>
    ) {
        val countFilter = filterBuilder.count.build(params.requestedLoadSize)
        val pageFilter = filterBuilder.page.build(params.key)
        val result = postsArena.suspendFetch(filterBuilder.build(countFilter, pageFilter, tagsFilter))
        val success = result.getOrNull() ?: return afterSignal.onNext(result)
        callback.onResult(success.deserializes, params.key + 1)
        afterSignal.onNext(result)
    }

    internal data class LastInitialSnapshot(
        val params: LoadInitialParams<Int>,
        val callback: LoadInitialCallback<Int, Result<PostDeserialize<Post>>>
    )

    internal data class LastAfterSnapshot(
        val params: LoadParams<Int>,
        val callback: LoadCallback<Int, Result<PostDeserialize<Post>>>
    )
}


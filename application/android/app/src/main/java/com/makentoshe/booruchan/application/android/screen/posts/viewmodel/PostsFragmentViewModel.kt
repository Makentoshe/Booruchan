package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.makentoshe.booruchan.application.android.FetchExecutor
import com.makentoshe.booruchan.application.android.MainExecutor
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsDataSource
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsPagedAdapter
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import io.reactivex.rxjava3.core.Observable

class PostsFragmentViewModel(
    private val postsArena: Arena<PostsFilter, PostsDeserialize<Post>>,
    private val filterBuilder: PostsFilter.Builder
) : ViewModel() {

    private val postsDataSource = PostsDataSource(postsArena, filterBuilder, viewModelScope)

    val postsAdapter by lazy { PostsPagedAdapter().apply { submitList(getPagedList()) } }

    // Indicates that the initial bath of data was already loaded
    val initialSignal: Observable<Result<*>> = postsDataSource.initialSignal

    fun retryLoadInitial() = postsDataSource.retryLoadInitial()

    private fun getPagedList(): PagedList<Result<PostDeserialize<Post>>> {
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(30).build()
        val pagedListBuilder = PagedList.Builder(postsDataSource, config)
        pagedListBuilder.setNotifyExecutor(MainExecutor())
        pagedListBuilder.setFetchExecutor(FetchExecutor(viewModelScope))
        return pagedListBuilder.build()
    }

    class Factory(
        private val postsArena: Arena<PostsFilter, PostsDeserialize<Post>>,
        private val postsFilterBuilder: PostsFilter.Builder
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsFragmentViewModel(postsArena, postsFilterBuilder) as T
        }
    }
}


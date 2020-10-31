package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.makentoshe.booruchan.application.android.FetchExecutor
import com.makentoshe.booruchan.application.android.MainExecutor
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsArena
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsArenaStorage
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsDataSource
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.network.PostsNetworkManager
import com.makentoshe.booruchan.core.post.network.PostsRequest

class PostsFragmentViewModel(
    private val booruContext: BooruContext,
    private val networkManager: PostsNetworkManager<PostsRequest>
) : ViewModel() {

    val title = booruContext.title

    val postsAdapter by lazy {
        val postsContext = booruContext.posts { networkManager.getPosts(it) } as PostsContext<PostsRequest, PostsFilter>
        val postsArena = PostsArena(postsContext, PostsArenaStorage())
        val dataSource = PostsDataSource(postsArena, postsContext.filterBuilder(), viewModelScope)
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(30).build()
        val pagedListBuilder = PagedList.Builder(dataSource, config)
        pagedListBuilder.setNotifyExecutor(MainExecutor())
        pagedListBuilder.setFetchExecutor(FetchExecutor(viewModelScope))
        return@lazy PostsPagedAdapter().apply { submitList(pagedListBuilder.build()) }
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


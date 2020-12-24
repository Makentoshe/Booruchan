package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makentoshe.booruchan.application.android.screen.posts.model.paging.PostEntity
import com.makentoshe.booruchan.application.android.screen.posts.model.paging.PostPagingSource
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.Tags
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.plus

class PostsFragmentViewModel(
    private val postsArena: Arena<PostsFilter, PostsDeserialize<Post>>,
    private val filterBuilder: PostsFilter.Builder
) : ViewModel() {

    /** Flow with initial paging data */
    val posts: Flow<PagingData<PostEntity>> = Pager(PagingConfig(30)) {
        PostPagingSource(postsArena, filterBuilder, filterBuilder.tags.build(Tags.EMPTY))
    }.flow.cachedIn(viewModelScope.plus(Dispatchers.IO))

    /** Returns flow with paging data for selected tags */
    fun posts(tags: Tags) = Pager(PagingConfig(30)) {
        PostPagingSource(postsArena, filterBuilder, filterBuilder.tags.build(tags))
    }.flow.cachedIn(viewModelScope.plus(Dispatchers.IO))

    class Factory(
        private val postsArena: Arena<PostsFilter, PostsDeserialize<Post>>,
        private val postsFilterBuilder: PostsFilter.Builder
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsFragmentViewModel(postsArena, postsFilterBuilder) as T
        }
    }
}

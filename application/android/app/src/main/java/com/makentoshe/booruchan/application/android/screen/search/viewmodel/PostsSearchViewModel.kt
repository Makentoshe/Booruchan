package com.makentoshe.booruchan.application.android.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.application.core.arena.tag.TagsArena
import com.makentoshe.booruchan.core.tag.context.TagsContext
import com.makentoshe.booruchan.core.tag.network.TagsFilter
import com.makentoshe.booruchan.core.tag.network.TagsRequest
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class PostsSearchViewModel(
    private val tagsArena: TagsArena,
    private val tagsContext: TagsContext<TagsRequest, TagsFilter>
) : ViewModel() {

    init {
        thread {
            runBlocking {
                val result = tagsArena.suspendFetch(tagsContext.filterBuilder().build())
                println(result)
            }
        }
    }

    class Factory(
        private val tagsArena: TagsArena,
        private val tagsContext: TagsContext<TagsRequest, TagsFilter>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsSearchViewModel(tagsArena, tagsContext) as T
        }
    }
}
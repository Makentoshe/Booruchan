package com.makentoshe.booruchan.application.android.screen.search.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.application.android.screen.search.model.PostsSearchTagsAutocompleteAdapter
import com.makentoshe.booruchan.application.core.arena.tag.TagsArena
import com.makentoshe.booruchan.core.tag.context.TagsContext
import com.makentoshe.booruchan.core.tag.network.TagsFilter
import com.makentoshe.booruchan.core.tag.network.TagsRequest

class PostsSearchViewModel(
    private val arena: TagsArena,
    private val tagsContext: TagsContext<TagsRequest, TagsFilter>
) : ViewModel() {

    // TODO store adapter pinned to selected context and recreate only if context was changed
    fun getTagsAutocompleteAdapter(context: Context): PostsSearchTagsAutocompleteAdapter {
        return PostsSearchTagsAutocompleteAdapter(context, arena, tagsContext)
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
package com.makentoshe.booruchan.application.android.screen.search.di

import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.application.android.BooruchanDatabase
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.search.PostsSearchFragment
import com.makentoshe.booruchan.application.android.screen.search.model.CompositeSearchTagsContainer
import com.makentoshe.booruchan.application.android.screen.search.model.TagsArenaStorage
import com.makentoshe.booruchan.application.android.screen.search.model.TypedSearchTagsContainer
import com.makentoshe.booruchan.application.android.screen.search.viewmodel.PostsSearchViewModel
import com.makentoshe.booruchan.application.core.arena.tag.TagsArena
import com.makentoshe.booruchan.application.core.network.TagsNetworkManager
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.tag.Type
import com.makentoshe.booruchan.core.tag.context.TagsContext
import com.makentoshe.booruchan.core.tag.network.TagsFilter
import com.makentoshe.booruchan.core.tag.network.TagsRequest
import io.ktor.client.*
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject

class PostsSearchModule(fragment: PostsSearchFragment) : Module() {

    private val booruContexts by inject<List<BooruContext>>()
    private val client by inject<HttpClient>()
    private val database by inject<BooruchanDatabase>(fragment.arguments.booruContextTitle)

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)
        val booruContext = booruContexts.first { it.title == fragment.arguments.booruContextTitle }
        bind<PostsSearchViewModel>().toInstance(buildViewModel(fragment, booruContext))

        val tagsContainer = CompositeSearchTagsContainer(
            TypedSearchTagsContainer(Type.ARTIST),
            TypedSearchTagsContainer(Type.METADATA),
            TypedSearchTagsContainer(Type.COPYRIGHT),
            TypedSearchTagsContainer(Type.CHARACTER),
            TypedSearchTagsContainer(Type.GENERAL)
        )
        bind<CompositeSearchTagsContainer>().toInstance(tagsContainer)
    }

    private fun buildViewModel(fragment: PostsSearchFragment, booruContext: BooruContext): PostsSearchViewModel {
        val tagsContext = booruContext.tags(TagsNetworkManager(client)::getTags) as TagsContext<TagsRequest, TagsFilter>
        val tagsArena = TagsArena(tagsContext, TagsArenaStorage())
        val viewModelFactory = PostsSearchViewModel.Factory(tagsArena, tagsContext)
        return ViewModelProviders.of(fragment, viewModelFactory)[PostsSearchViewModel::class.java]
    }
}

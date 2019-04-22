package com.makentoshe.booruchan.screen.posts

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.screen.posts.controller.*
import com.makentoshe.booruchan.screen.posts.viewmodel.SearchState
import com.makentoshe.booruchan.screen.posts.viewmodel.SearchStateViewModel
import com.makentoshe.booruchan.screen.posts.viewmodel.TagsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object PostsModule {
    const val booruStr = "PostsBooru"
    const val fragmentStr = "PostsFragment"

    val module = module {
        scope(named<PostsFragment>()) {
            scoped(named(fragmentStr)) { (fragment: Fragment) -> fragment }
            scoped(named(booruStr)) { (booruHolder: BooruHolder) -> booruHolder }
            scoped { (searchState: SearchState) -> searchState }

            scoped { ToolbarController(get<BooruHolder>(named(booruStr)).booru) }

            scoped { (searchState: SearchState) -> BottomBarController(searchState) }

            scoped { (searchState: SearchState) ->
                val fragmentManager = get<Fragment>(named(fragmentStr)).childFragmentManager
                val booru = get<BooruHolder>(named(booruStr)).booru
                ViewPagerController(booru, searchState, fragmentManager)
            }

            scoped { (tags: TagsViewModel) ->
                val booru = get<BooruHolder>(named(booruStr)).booru
                val fragment = get<Fragment>(named(fragmentStr))
                MagnifyController(booru, fragment, tags)
            }

            viewModel { (tags: Set<Tag>) -> TagsViewModel(tags) }

            viewModel { (tagsViewModel: TagsViewModel, initialTags: Set<Tag>) ->
                SearchStateViewModel(get(), initialTags, tagsViewModel, SearchController())
            }
        }
    }
}

package com.makentoshe.booruchan.screen.posts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.screen.posts.controller.*
import com.makentoshe.booruchan.screen.posts.viewmodel.TagsViewModel
import com.makentoshe.booruchan.screen.posts.viewmodel.SearchState
import com.makentoshe.booruchan.screen.posts.viewmodel.SearchStateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildPostsScope() {
    scope(named<PostsFragment>()) {
        scoped { (searchStateViewModel: SearchStateViewModel) ->
            BottomBarController(
                searchStateViewModel
            )
        }
        scoped { (booru: Booru) -> ToolbarController(booru) }

        scoped { (booru: Booru, searchState: SearchState, fragmentManager: FragmentManager) ->
            ViewPagerController(booru, searchState, fragmentManager)
        }

        scoped { (booru: Booru, fragment: Fragment, tags: TagsViewModel) ->
            MagnifyController(booru, fragment, tags)
        }

        viewModel { (tags: Set<Tag>) -> TagsViewModel(tags) }
        viewModel { (tagsViewModel: TagsViewModel, initialTags: Set<Tag>) ->
            SearchStateViewModel(
                Booruchan.INSTANCE, initialTags, tagsViewModel,
                SearchController()
            )
        }
    }
}

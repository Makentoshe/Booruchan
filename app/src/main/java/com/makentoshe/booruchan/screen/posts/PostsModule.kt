package com.makentoshe.booruchan.screen.posts

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolderImpl
import com.makentoshe.booruchan.screen.posts.controller.*
import com.makentoshe.booruchan.screen.posts.viewmodel.PostsViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.koin.getViewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

object PostsModule {
    const val FRAGMENT = "PostsFragment"

    private fun Scope.getPostsViewModel(): PostsViewModel {
        val fragment = get<Fragment>(named(FRAGMENT))
        return getViewModel(fragment)
    }

    val module = module {
        scope(named<PostsFragment>()) {
            /* provide a fragment instance to the scope */
            scoped(named(FRAGMENT)) { (fragment: Fragment) -> fragment }

            scoped {
                val viewModel = getPostsViewModel()
                ToolbarController(viewModel.booru)
            }

            scoped {
                val viewModel = getPostsViewModel()
                PostsMagnifyController(viewModel, get(named(FRAGMENT)), viewModel)
            }

            scoped {
                val viewModel = getPostsViewModel()
                val left = BottomBarLeftController(viewModel)
                val center = BottomBarCenterController(viewModel)
                val right = BottomBarRightController()
                BottomBarController(left, center, right)
            }

            scoped {
                val viewModel = getPostsViewModel()
                val fragment = get<Fragment>(named(FRAGMENT))
                ViewPagerController(viewModel, viewModel, fragment.childFragmentManager)
            }
        }

        /* Initial tags and booru impl */
        viewModel { (tags: Set<Tag>, booru: Booru) ->
            val tagsHolder = TagsHolderImpl(tags)
            val booruHolder = BooruHolderImpl(booru)
            val disposables = CompositeDisposable()
            val searchRxProvider = SearchRxProvider(disposables)
            val cacheController = CacheControllerImpl(get())
            PostsViewModel(
                tagsHolder,
                booruHolder,
                searchRxProvider,
                disposables,
                cacheController
            )
        }
    }
}


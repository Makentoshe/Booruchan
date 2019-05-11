package com.makentoshe.booruchan.screen.posts.container

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolderImpl
import com.makentoshe.booruchan.model.TagsHolderImpl
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.posts.container.controller.*
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.koin.getViewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

object PostsModule {
    val module = module {

        factory { (b: Booru) -> PostsToolbarController(b) }

        factory { (b: Booru, t: Set<Tag>, f: Fragment) -> PostsMagnifyController(b, f, t) }

        factory { (sc: SearchController) ->
            val left = BottomBarLeftController(sc)
            val center = BottomBarCenterController(sc)
            val right = BottomBarRightController()
            PostsBottomBarController(left, center, right)
        }

        factory { (b: Booru, sc:SearchController, fm: FragmentManager ) ->
            PostsViewPagerController(b, sc, fm)
        }

        factory {
            val postsCache = PostInternalCache(get())
            val sampleCache = ImageInternalCache(get(), InternalCache.Type.SAMPLE)
            val previewCache = ImageInternalCache(get(), InternalCache.Type.PREVIEW)
            val fileCache = ImageInternalCache(get(), InternalCache.Type.FILE)
            CacheController.create(postsCache, sampleCache, previewCache, fileCache)
        }

        viewModel { (t: Set<Tag>, d: CompositeDisposable) ->
            val searchRxProvider = SearchRxProvider(d)
            PostsViewModel(searchRxProvider, d, get()).apply { startSearch(t) }
        }
    }
}


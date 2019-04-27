package com.makentoshe.booruchan.screen.posts.page

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolderImpl
import com.makentoshe.booruchan.model.PositionHolderImpl
import com.makentoshe.booruchan.model.TagsHolderImpl
import com.makentoshe.booruchan.repository.factory.CachedRepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.controller.PostPageContentRouter
import com.makentoshe.booruchan.screen.posts.page.controller.PostsPageContentController
import com.makentoshe.booruchan.screen.posts.page.controller.SampleScreenBuilder
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.GridElementControllerBuilder
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.GridElementTypeControllerBuilder
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadController
import com.makentoshe.booruchan.screen.posts.page.model.GridAdapterBuilder
import com.makentoshe.booruchan.screen.posts.page.view.PostPageGridElementUiBuilder
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.koin.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

object PostsPageModule {

    const val FRAGMENT = "PostsPageFragment"
    const val DISPOSABLE = "PostsPageDisposables"

    private fun Scope.getViewModel(): PostsPageViewModel {
        val fragment = get<Fragment>(named(FRAGMENT))
        return getViewModel(fragment)
    }

    val module = module {

        scope(named<PostsPageFragment>()) {
            scoped(named(FRAGMENT)) { (fragment: Fragment) -> fragment }
            scoped(named(DISPOSABLE)) { CompositeDisposable() }
            scoped { PostPageGridElementUiBuilder() }
            scoped { GridElementTypeControllerBuilder() }
            scoped {
                val repositoryFactory = get<CachedRepositoryFactory> { parametersOf(getViewModel().booru) }
                GridElementControllerBuilder(get(named(DISPOSABLE)), repositoryFactory, get())
            }
            scoped { GridAdapterBuilder(get(), get()) }
            scoped { PostsPageContentController(getViewModel(), get(), getViewModel(), get()) }
            scoped { SampleScreenBuilder(getViewModel(), getViewModel()) }
            scoped { PostPageContentRouter(get(), get()) }
        }


        viewModel { (booru: Booru, tags: Set<Tag>, position: Int, disposables: CompositeDisposable) ->
            val booruHolder = BooruHolderImpl(booru)
            val tagsHolder = TagsHolderImpl(tags)
            val positionHolder = PositionHolderImpl(position)

            val postsDownloadController = get<PostsDownloadController> { parametersOf(booru, disposables) }

            PostsPageViewModel(booruHolder, tagsHolder, positionHolder, postsDownloadController)
        }
    }
}

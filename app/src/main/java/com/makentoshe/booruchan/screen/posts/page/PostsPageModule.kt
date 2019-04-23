package com.makentoshe.booruchan.screen.posts.page

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolderImpl
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.repository.factory.CachedRepositoryFactory
import com.makentoshe.booruchan.screen.posts.container.controller.TagsHolderImpl
import com.makentoshe.booruchan.screen.posts.page.controller.PostsPageContentController
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.PostPageGridElementControllerFactory
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadControllerFactory
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadControllerImpl
import com.makentoshe.booruchan.screen.posts.page.model.PostPageGridAdapterFactory
import com.makentoshe.booruchan.screen.posts.page.view.PostPageGridElementUiFactory
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

            scoped { PostPageGridElementUiFactory() }

            scoped(named(DISPOSABLE)) { CompositeDisposable() }
            /* composite disposable */
            scoped { PostsPreviewImageDownloadControllerFactory(get(named(DISPOSABLE))) }
            /* download controller factory, repository factory */
            scoped {
                val repositoryFactory = get<CachedRepositoryFactory> { parametersOf(getViewModel().booru) }
                PostPageGridElementControllerFactory(get(), repositoryFactory) }
            /* ui factory, controller factory */
            scoped { PostPageGridAdapterFactory(get(), get()) }
            /* adapter factory */
            scoped { PostsPageContentController(getViewModel(), get()) }
        }

        viewModel { (booru: Booru, tags: Set<Tag>, position: Int) ->
            val repositoryFactory = get<CachedRepositoryFactory> { parametersOf(booru) }
            val booruHolder = BooruHolderImpl(booru)
            val tagsHolder = TagsHolderImpl(tags)
            val disposables = CompositeDisposable()
            val request = get<Posts.Request> { parametersOf(tags, position) }
            val postsDownloadController = PostsDownloadControllerImpl(repositoryFactory, request, disposables)
            PostsPageViewModel(booruHolder, tagsHolder, position, postsDownloadController, disposables)
        }
    }
}
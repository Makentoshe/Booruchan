package com.makentoshe.booruchan.screen.posts.page

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolderImpl
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.cache.ClearableCache
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
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

    private fun Scope.getPreviewImageCache(): ImageInternalCache {
        return ImageInternalCache(get(), InternalCache.Type.PREVIEW)
    }

    private fun Scope.getPreviewImageRepository(booru: Booru = getViewModel().booru): PreviewImageRepository {
        return PreviewImageRepository(booru)
    }

    private fun Scope.getPreviewImageCachedRepository(booru: Booru = getViewModel().booru): CachedRepository<Post, ByteArray> {
        return CachedRepository(getPreviewImageCache(), getPreviewImageRepository(booru))
    }

    private fun Scope.getPostsRepository(booru: Booru = getViewModel().booru): Repository<Posts.Request, List<Post>> {
        return PostsRepository(booru)
    }

    private fun Scope.getPostsCache(): ClearableCache<Posts.Request, List<Post>?> {
        return PostInternalCache(get())
    }

    private fun Scope.getPostsCachedRepository(booru: Booru = getViewModel().booru): Repository<Posts.Request, List<Post>> {
        return CachedRepository(getPostsCache(), getPostsRepository(booru))
    }

    val module = module {

        scope(named<PostsPageFragment>()) {

            scoped(named(FRAGMENT)) { (fragment: Fragment) -> fragment }

            scoped { PostPageGridElementUiFactory() }

            scoped(named(DISPOSABLE)) { CompositeDisposable() }
            /* composite disposable */
            scoped { PostsPreviewImageDownloadControllerFactory(get(named(DISPOSABLE))) }
            /* download controller factory */
            scoped { PostPageGridElementControllerFactory(get(), getPreviewImageCachedRepository()) }
            /* ui factory, controller factory */
            scoped { PostPageGridAdapterFactory(get(), get()) }
            /* adapter factory */
            scoped { PostsPageContentController(getViewModel(), get()) }
        }

        viewModel { (booru: Booru, tags: Set<Tag>, position: Int) ->
            val booruHolder = BooruHolderImpl(booru)
            val tagsHolder = TagsHolderImpl(tags)
            val disposables = CompositeDisposable()
            val request = get<Posts.Request> { parametersOf(tags, position) }
            val repository = getPostsCachedRepository(booru)
            val postsDownloadController = PostsDownloadControllerImpl(repository, request, disposables)
            PostsPageViewModel(booruHolder, tagsHolder, position, postsDownloadController, disposables)
        }
    }
}
package com.makentoshe.booruchan.screen.posts.page

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolderImpl
import com.makentoshe.booruchan.repository.factory.CachedRepositoryFactory
import com.makentoshe.booruchan.screen.posts.container.controller.TagsHolderImpl
import com.makentoshe.booruchan.screen.posts.page.controller.PostsPageContentController
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.PostPageGridElementControllerFactory
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadControllerFactory
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadControllerImpl
import com.makentoshe.booruchan.screen.posts.page.model.PostPageGridAdapterFactory
import com.makentoshe.booruchan.screen.posts.page.view.PostPageGridElementUiFactory
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.find
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.koin.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import java.io.File

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

            scoped { GridElementTypeControllerFactory() }

            scoped(named(DISPOSABLE)) { CompositeDisposable() }
            /* composite disposable */
            scoped { PostsPreviewImageDownloadControllerFactory(get(named(DISPOSABLE))) }
            /* download controller factory, repository factory */
            scoped {
                val repositoryFactory = get<CachedRepositoryFactory> { parametersOf(getViewModel().booru) }
                PostPageGridElementControllerFactory(get(), repositoryFactory, get())
            }
            /* ui factory, controller factory */
            scoped { PostPageGridAdapterFactory(get(), get()) }
            /* adapter factory */
            scoped { PostsPageContentController(getViewModel(), get()) }
        }

        viewModel { (booru: Booru, tags: Set<Tag>, position: Int, disposables: CompositeDisposable) ->
            val repositoryFactory = get<CachedRepositoryFactory> { parametersOf(booru) }
            val booruHolder = BooruHolderImpl(booru)
            val tagsHolder = TagsHolderImpl(tags)
            val request = get<Posts.Request> { parametersOf(tags, position) }
            val postsDownloadController = PostsDownloadControllerImpl(repositoryFactory, request, disposables)
            PostsPageViewModel(booruHolder, tagsHolder, position, postsDownloadController)
        }
    }
}

class GridElementTypeControllerFactory {
    fun buildController(post: Post): GridElementTypeController {
        return GridElementTypeController(post)
    }
}

class GridElementTypeController(private val post: Post) {

    fun bindView(view: View) {

        when (File(post.fileUrl).extension) {
            "webm" -> videoType(view)
            "gif" -> animationType(view)
            else -> defaultType(view)
        }
    }

    private fun videoType(view: View) {
        val videoDrawable = view.context.getDrawable(R.drawable.ic_video)!!
        view.setType(videoDrawable)
    }

    private fun animationType(view: View) {
        val animationDrawable = view.context.getDrawable(R.drawable.ic_animation)!!
        view.setType(animationDrawable)
    }

    private fun defaultType(view: View) {
        val typeview = view.find<ImageView>(R.id.posts_page_gridview_element_type)
        typeview.visibility = View.GONE
    }

    private fun View.setType(typeDrawable: Drawable) {
        val typeview = find<ImageView>(R.id.posts_page_gridview_element_type)
        typeDrawable.mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
        typeview.setImageDrawable(typeDrawable)
    }
}
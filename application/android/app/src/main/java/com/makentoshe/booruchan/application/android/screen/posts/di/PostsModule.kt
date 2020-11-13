package com.makentoshe.booruchan.application.android.screen.posts.di

import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.application.android.BooruchanDatabase
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.booru.navigation.BooruNavigation
import com.makentoshe.booruchan.application.android.screen.posts.model.PostPreviewArenaStorage
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsArenaStorage
import com.makentoshe.booruchan.application.android.screen.posts.view.PostsFragment
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsFragmentViewModel
import com.makentoshe.booruchan.application.core.arena.post.PostImageArena
import com.makentoshe.booruchan.application.core.arena.post.PostsArena
import com.makentoshe.booruchan.application.core.network.PostsNetworkManager
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.network.PostsRequest
import io.ktor.client.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject
import java.io.File

class PostsModule(fragment: PostsFragment) : Module() {

    private val booruContexts by inject<List<BooruContext>>()
    private val client by inject<HttpClient>()
    private val database by inject<BooruchanDatabase>(fragment.arguments.booruContextTitle)

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)
        val booruContext = booruContexts.first { it.title == fragment.arguments.booruContextTitle }

        bindPostsFragmentViewModel(fragment, booruContext)

        val navigation = BooruNavigation(fragment.childFragmentManager, booruContext)
        bind<BooruNavigation>().toInstance(navigation)

        val fragmentDisposable = CompositeDisposable()
        bind<CompositeDisposable>().toInstance(fragmentDisposable)
    }

    private fun bindPostsFragmentViewModel(fragment: PostsFragment, booruContext: BooruContext) {
        val postsContext = getPostsBooruContext(booruContext)
        val postsArena = getPostsArena(booruContext, postsContext)
        val previewArena = getPreviewArena(booruContext, fragment)
        val postsFragmentViewModelFactory =
            PostsFragmentViewModel.Factory(postsArena, previewArena, postsContext.filterBuilder())
        val postsFragmentViewModelProvider = ViewModelProviders.of(fragment, postsFragmentViewModelFactory)
        val postsFragmentViewModel = postsFragmentViewModelProvider[PostsFragmentViewModel::class.java]
        bind<PostsFragmentViewModel>().toInstance(postsFragmentViewModel)
    }

    private fun getPostsBooruContext(booruContext: BooruContext): PostsContext<PostsRequest, PostsFilter> {
        val postsNetworkManager = PostsNetworkManager(client)
        return booruContext.posts { postsNetworkManager.getPosts(it) } as PostsContext<PostsRequest, PostsFilter>
    }

    private fun getPostsArena(
        booruContext: BooruContext, postsContext: PostsContext<PostsRequest, PostsFilter>
    ) = PostsArena.Builder(booruContext).apply {
        arenaStorage = PostsArenaStorage(database, postsContext)
    }.build(client)

    private fun getPreviewArena(booruContext: BooruContext, fragment: PostsFragment): PostImageArena {
        val cacheDir = File(fragment.requireContext().cacheDir, booruContext.title)
        return PostImageArena(client, PostPreviewArenaStorage(cacheDir))
    }
}
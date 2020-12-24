package com.makentoshe.booruchan.application.android.screen.posts.di

import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.makentoshe.booruchan.application.android.arena.PostsArenaCache
import com.makentoshe.booruchan.application.android.arena.PreviewContentArenaCache
import com.makentoshe.booruchan.application.android.database.BooruchanDatabase
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.booru.navigation.BooruNavigation
import com.makentoshe.booruchan.application.android.screen.posts.PostsFragment
import com.makentoshe.booruchan.application.android.screen.posts.model.paging.PostAdapter
import com.makentoshe.booruchan.application.android.screen.posts.model.paging.PostStateAdapter
import com.makentoshe.booruchan.application.android.screen.posts.navigation.PostsNavigation
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsFragmentViewModel
import com.makentoshe.booruchan.application.core.arena.post.PostContentArena
import com.makentoshe.booruchan.application.core.arena.post.PostsArena
import com.makentoshe.booruchan.application.core.network.PostsNetworkManager
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.network.PostsRequest
import io.ktor.client.*
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject
import java.io.File

annotation class PostsScope

// TODO add base module with extracting/defining booruContext
class PostsModule(fragment: PostsFragment) : Module() {

    private val booruContexts by inject<List<BooruContext>>()
    private val client by inject<HttpClient>()
    private val database by inject<BooruchanDatabase>(fragment.arguments.booruclass.simpleName)
    private val router by inject<Router>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)
        val booruContext = booruContexts.first { it.javaClass == fragment.arguments.booruclass }

        val fragmentNavigation = PostsNavigation(router, booruContext.javaClass)
        val previewArena = getPreviewArena(booruContext, fragment)
        val adapter = PostAdapter(previewArena, fragment.lifecycleScope, fragmentNavigation)
        bind<PostAdapter>().toInstance(adapter)
        bind<PostStateAdapter>().toInstance(PostStateAdapter(adapter))

        val navigation = BooruNavigation(fragment.childFragmentManager, booruContext)
        bind<BooruNavigation>().toInstance(navigation)

        bindPostsFragmentViewModel(fragment, booruContext)
    }

    private fun bindPostsFragmentViewModel(fragment: PostsFragment, booruContext: BooruContext) {
        val postsContext = getPostsBooruContext(booruContext)
        val postsArena = getPostsArena(booruContext, postsContext)

        val viewModelFactory = PostsFragmentViewModel.Factory(postsArena, postsContext.filterBuilder())
        val viewModelProvider = ViewModelProviders.of(fragment, viewModelFactory)
        val viewModel = viewModelProvider[PostsFragmentViewModel::class.java]
        bind<PostsFragmentViewModel>().toInstance(viewModel)
    }

    private fun getPostsBooruContext(booruContext: BooruContext): PostsContext<PostsRequest, PostsFilter> {
        val postsNetworkManager = PostsNetworkManager(client)
        return booruContext.posts { postsNetworkManager.getPosts(it) } as PostsContext<PostsRequest, PostsFilter>
    }

    private fun getPostsArena(
        booruContext: BooruContext, postsContext: PostsContext<PostsRequest, PostsFilter>
    ) = PostsArena.Builder(booruContext).apply {
        arenaStorage = PostsArenaCache(database.postsDao(), postsContext)
    }.build(client)

    private fun getPreviewArena(booruContext: BooruContext, fragment: PostsFragment): PostContentArena {
        val cacheDir = File(fragment.requireContext().cacheDir, booruContext.title)
        return PostContentArena(client, PreviewContentArenaCache(database.previewContentDao(), cacheDir))
    }
}
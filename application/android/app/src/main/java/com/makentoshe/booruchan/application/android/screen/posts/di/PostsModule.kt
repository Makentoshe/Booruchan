package com.makentoshe.booruchan.application.android.screen.posts.di

import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.booru.navigation.BooruNavigation
import com.makentoshe.booruchan.application.android.screen.posts.view.PostsFragment
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsFragmentViewModel
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsNetworkManager
import com.makentoshe.booruchan.core.context.BooruContext
import io.ktor.client.*
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject

class PostsModule(fragment: PostsFragment) : Module() {

    private val booruContexts by inject<List<BooruContext>>()
    private val client by inject<HttpClient>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)

        val postsNetworkManager = PostsNetworkManager(client)
        val booruContext = booruContexts.first { it.title == fragment.arguments.booruContextTitle }
        val postsFragmentViewModelFactory = PostsFragmentViewModel.Factory(booruContext, postsNetworkManager)
        val postsFragmentViewModelProvider = ViewModelProviders.of(fragment, postsFragmentViewModelFactory)
        val postsFragmentViewModel = postsFragmentViewModelProvider[PostsFragmentViewModel::class.java]
        bind<PostsFragmentViewModel>().toInstance(postsFragmentViewModel)

        val navigation = BooruNavigation(fragment.childFragmentManager, booruContext)
        bind<BooruNavigation>().toInstance(navigation)
    }
}
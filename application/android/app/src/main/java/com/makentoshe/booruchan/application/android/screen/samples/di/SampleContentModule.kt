package com.makentoshe.booruchan.application.android.screen.samples.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.application.android.arena.PreviewContentArenaCache
import com.makentoshe.booruchan.application.android.database.BooruchanDatabase
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.samples.SampleContentFragment
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleContentFragmentViewModel
import com.makentoshe.booruchan.application.core.arena.post.PostContentArena
import com.makentoshe.booruchan.core.context.BooruContext
import io.ktor.client.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject
import java.io.File
import javax.inject.Qualifier

@Qualifier
annotation class SampleContentScope

class SampleContentModule(fragment: SampleContentFragment) : Module() {

    private val booruContexts by inject<List<BooruContext>>()
    private val client by inject<HttpClient>()
    private val database by inject<BooruchanDatabase>(fragment.arguments.booruclass.simpleName)

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)
        val booruContext = booruContexts.first { it.javaClass == fragment.arguments.booruclass }
        // Fragment composite disposable
        bind<CompositeDisposable>().withName(SampleContentScope::class).toInstance(CompositeDisposable())

        val previewArena = getPreviewArena(booruContext, fragment)
        val contentFactory = SampleContentFragmentViewModel.Factory(previewArena, fragment.arguments.post)
        val contentViewModel =
            ViewModelProviders.of(fragment, contentFactory)[SampleContentFragmentViewModel::class.java]
        bind<SampleContentFragmentViewModel>().toInstance(contentViewModel)
    }

    private fun getPreviewArena(booruContext: BooruContext, fragment: Fragment): PostContentArena {
        val cacheDir = File(fragment.requireContext().cacheDir, booruContext.title)
        return PostContentArena(client, PreviewContentArenaCache(database.previewContentDao(), cacheDir))
    }
}
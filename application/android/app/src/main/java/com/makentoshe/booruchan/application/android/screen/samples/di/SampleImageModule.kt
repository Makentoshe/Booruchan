package com.makentoshe.booruchan.application.android.screen.samples.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.application.android.arena.PreviewContentArenaStorage
import com.makentoshe.booruchan.application.android.database.BooruchanDatabase
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.samples.SampleImageFragment
import com.makentoshe.booruchan.application.android.screen.samples.model.PostSampleArenaStorage
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleImageFragmentViewModel
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
annotation class SampleImageScope

class SampleImageModule(fragment: SampleImageFragment) : Module() {

    private val booruContexts by inject<List<BooruContext>>()
    private val client by inject<HttpClient>()
    private val database by inject<BooruchanDatabase>(fragment.arguments.booruclass.simpleName)

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)
        val booruContext = booruContexts.first { it.javaClass == fragment.arguments.booruclass }
        bind<CompositeDisposable>().withName(SampleImageScope::class).toInstance(CompositeDisposable())

        val sampleViewModel = getSampleViewModel(fragment, booruContext)
        bind<SampleImageFragmentViewModel>().toInstance(sampleViewModel)
    }

    private fun getSampleArena(booruContext: BooruContext, fragment: Fragment): PostContentArena {
        val cacheDir = File(fragment.requireContext().cacheDir, booruContext.title)
        return PostContentArena(client, PostSampleArenaStorage(cacheDir))
    }

    private fun getPreviewArena(booruContext: BooruContext, fragment: Fragment): PostContentArena {
        val cacheDir = File(fragment.requireContext().cacheDir, booruContext.title)
        return PostContentArena(client, PreviewContentArenaStorage(database.previewContentDao(), cacheDir))
    }

    private fun getSampleViewModel(fragment: SampleImageFragment, booruContext: BooruContext): SampleImageFragmentViewModel {
        val previewArena = getPreviewArena(booruContext, fragment)
        val sampleArena = getSampleArena(booruContext, fragment)
        val imageFactory = SampleImageFragmentViewModel.Factory(fragment.arguments.post, previewArena, sampleArena)
        return ViewModelProviders.of(fragment, imageFactory)[SampleImageFragmentViewModel::class.java]
    }
}
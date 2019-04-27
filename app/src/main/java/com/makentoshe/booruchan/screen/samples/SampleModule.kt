package com.makentoshe.booruchan.screen.samples

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadController
import com.makentoshe.booruchan.screen.samples.controller.SampleContentController
import com.makentoshe.booruchan.screen.samples.controller.SamplePageContentController
import com.makentoshe.booruchan.screen.samples.controller.SampleSwipeBottomBarController
import com.makentoshe.booruchan.screen.samples.controller.SampleSwipeContentController
import com.makentoshe.booruchan.screen.samples.fragment.SampleFragment
import com.makentoshe.booruchan.screen.samples.fragment.SamplePageFragment
import com.makentoshe.booruchan.screen.samples.fragment.SampleSwipeFragment
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.koin.getViewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import java.util.*

object SampleModule {

    val module = org.koin.dsl.module {
        sampleFragmentScope
        sampleSwipeFragmentScope
        samplePageFragmentScope
    }

    private val Module.sampleFragmentScope: Unit
        get() = scope(named<SampleFragment>()) {
            scoped { (fragment: SampleFragment) -> fragment }
            scoped { (booru: Booru, tags: Set<Tag>, position: Int) ->
                SampleContentController(
                    booru,
                    tags,
                    position,
                    get<SampleFragment>().childFragmentManager
                )
            }
        }

    private val Module.sampleSwipeFragmentScope: Unit
        get() = scope(named<SampleSwipeFragment>()) {
            scoped { (fragment: SampleSwipeFragment) -> fragment }
            scoped { (booru: Booru, tags: Set<Tag>) ->
                SampleSwipeBottomBarController(booru, tags)
            }
            scoped { (booru: Booru, tags: Set<Tag>, position: Int) ->
                val fragmentManager = get<SampleSwipeFragment>().childFragmentManager
                SampleSwipeContentController(
                    booru,
                    tags,
                    position,
                    fragmentManager
                )
            }
        }

    private fun Scope.getSamplePageViewModel(): SamplePageViewModel {
        val fragment = get<SamplePageFragment>()
        return getViewModel(fragment)
    }

    private val Module.samplePageFragmentScope: Unit
        get() = scope(named<SamplePageFragment>()) {
            scoped { (fragment: SamplePageFragment) -> fragment }
            scoped { SamplePageContentController(getSamplePageViewModel()) }

            viewModel { (booru: Booru, tags: Set<Tag>, position: Int, disposables: CompositeDisposable) ->
                val postsDownloadController = get<PostsDownloadController> { parametersOf(booru, disposables) }
                SamplePageViewModel(booru, HashSet(tags), position, postsDownloadController)
            }
        }
}

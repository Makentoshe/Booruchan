package com.makentoshe.booruchan.screen.samples

import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.model.StreamDownloadListener
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.GifDownloadListener
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadListener
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadController
import com.makentoshe.booruchan.screen.samples.controller.*
import com.makentoshe.booruchan.screen.samples.fragment.*
import com.makentoshe.booruchan.screen.samples.model.SamplePageConcreteFragmentFactory
import com.makentoshe.booruchan.screen.samples.model.SamplePageFragmentRouter
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.koin.getViewModel
import org.koin.core.KoinComponent
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import java.util.*

object SampleModule : KoinComponent {

    val module = org.koin.dsl.module {
        /* Controller for the sample progress bar */
        factory { (l: StreamDownloadListener) -> CircularProgressBarController(l) }

        sampleFragmentScope
        sampleSwipeFragmentScope
        samplePageFragmentScope

        viewModel { (b: Booru, p: Post, d: CompositeDisposable, c: StreamDownloadController) ->
            SamplePageImageViewModel(b, p, d, c)
        }

        scope(named<SamplePageImageFragment>()) {
            scoped { (l: ImageDownloadListener) -> SamplePageImageController(l) }
        }

        viewModel { (b: Booru, p: Post, d: CompositeDisposable, c: StreamDownloadController) ->
            SamplePageGifViewModel(b, p, d, c)
        }

        scope(named<SamplePageGifFragment>()) {
            scoped { (l: GifDownloadListener) -> SamplePageGifController(l) }
        }

        scope(named<SamplePageWebmFragment>()) {
            scoped { (b: Booru, p: Post, fm: FragmentManager) -> SamplePageWebmController(b, p, fm) }
        }

    }

    private val Module.sampleFragmentScope: Unit
        get() = scope(named<SampleFragment>()) {
            scoped { (fragment: SampleFragment) -> fragment }
            scoped { (booru: Booru, tags: Set<Tag>, position: Int) ->
                SampleController(
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
                SampleSwipeController(
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

    const val PAGE_DISPOSABLE = "SamplePageFragmentDisposables"
    private val Module.samplePageFragmentScope: Unit
        get() = scope(named<SamplePageFragment>()) {
            scoped { (fragment: SamplePageFragment) -> fragment }
            scoped(named(PAGE_DISPOSABLE)) { (d: CompositeDisposable) -> d }

            scoped {
                val vm = getSamplePageViewModel()
                val d = get<CompositeDisposable>(named(PAGE_DISPOSABLE))
                val prevDownCtrl = get<PreviewImageDownloadController> { parametersOf(vm.booru, d) }
                val fragmentFactory = SamplePageConcreteFragmentFactory(vm.booru)
                val router =
                    SamplePageFragmentRouter(get<SamplePageFragment>().childFragmentManager)
                SamplePageController(vm, prevDownCtrl, fragmentFactory, router)
            }

            viewModel { (b: Booru, t: Set<Tag>, p: Int, d: CompositeDisposable) ->
                val pdc = get<PostsDownloadController> { parametersOf(b, d) }
                SamplePageViewModel(b, HashSet(t), p, pdc)
            }
        }
}

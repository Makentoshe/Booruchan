package com.makentoshe.booruchan.screen.samples

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.screen.samples.controller.SampleContentController
import com.makentoshe.booruchan.screen.samples.fragment.SampleFragment
import com.makentoshe.booruchan.screen.samples.fragment.SampleSwipeFragment
import com.makentoshe.booruchan.screen.samples.controller.SampleSwipeBottomBarController
import com.makentoshe.booruchan.screen.samples.controller.SampleSwipeContentController
import org.koin.core.module.Module
import org.koin.core.qualifier.named

object SampleModule {

    val module = org.koin.dsl.module {
        sampleFragmentScope
        sampleSwipeFragmentScope
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
}

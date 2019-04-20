package com.makentoshe.booruchan.screen.booru

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.CiceroneFactory
import com.makentoshe.booruchan.navigation.FragmentNavigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildBooruScope() {
    scope(named<BooruFragment>()) {
        scoped { (booru: Booru, tags: Set<Tag>) -> LocalRouter(booru, tags) }
        scoped { (activity: FragmentActivity, fragmentManager: FragmentManager) ->
            FragmentNavigator(activity, R.id.booru_drawer_content, fragmentManager)
        }
    }
    factory { CiceroneFactory() }
    viewModel { (localRouter: LocalRouter) -> LocalNavigatorViewModel(get(), localRouter) }
}

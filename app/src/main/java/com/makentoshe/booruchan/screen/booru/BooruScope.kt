package com.makentoshe.booruchan.screen.booru

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val booruModule = module {
    val ciceroneStr = "BooruCicerone"
    val fragmentStr = "BooruFragment"

    scope(named<BooruFragment>()) {
        scoped { (booru: Booru) -> booru }
        scoped { (tags: Set<Tag>) -> tags }
        scoped(named(fragmentStr)) { (fragment: Fragment) -> fragment }
        scoped { get<Fragment>(named(fragmentStr)).requireActivity() }
        scoped { get<Fragment>(named(fragmentStr)).childFragmentManager }
        scoped(named(ciceroneStr)) { Cicerone.create(Router()) }

        scoped { LocalRouter(get(), get()) }
        scoped { FragmentNavigator(get(), R.id.booru_drawer_content, get()) }

        viewModel { (cicerone: Cicerone<Router>, localRouter: LocalRouter) ->
            LocalNavigatorViewModel(cicerone, localRouter)
        }
    }
}
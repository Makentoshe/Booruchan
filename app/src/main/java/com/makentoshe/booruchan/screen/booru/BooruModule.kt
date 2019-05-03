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

object BooruModule {
    const val fragmentStr = "BooruFragment"
    const val ciceroneStr = "BooruCicerone"
    const val booruStr = "BooruBooru"
    const val tagsStr = "BooruTags"
    private const val actStr = "BooruFragmentActivity"
    private const val fmStr = "BooruFragmentManager"

    val module = module {
        scope(named<BooruFragment>()) {
            scoped(named(booruStr)) { (booru: Booru) -> booru }
            scoped(named(tagsStr)) { (tags: Set<Tag>) -> tags }
            scoped(named(fragmentStr)) { (fragment: Fragment) -> fragment }
            scoped(named(actStr)) { get<Fragment>(named(fragmentStr)).requireActivity() }
            scoped(named(fmStr)) { get<Fragment>(named(fragmentStr)).childFragmentManager }
            scoped(named(ciceroneStr)) { Cicerone.create(Router()) }

            scoped { LocalRouter(get(named(booruStr)), get(named(tagsStr))) }
            scoped { FragmentNavigator(get(named(actStr)), R.id.booru_drawer_content, get(named(fmStr))) }

            viewModel { (cicerone: Cicerone<Router>, localRouter: LocalRouter) ->
                LocalNavigatorViewModel(cicerone, localRouter)
            }
        }
    }
}
package com.makentoshe.booruchan.screen.booru

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

object BooruModule {
    val module = module {

        factory { (fa: FragmentActivity, fm: FragmentManager) ->
            FragmentNavigator(fa, R.id.booru_drawer_content, fm)
        }

        viewModel { (b: Booru, t: Set<Tag>) ->
            val router = Router()
            val localRouter = LocalRouter(b, t, router)
            val cicerone = Cicerone.create(router)
            LocalNavigatorViewModel(cicerone, localRouter)
        }
    }
}
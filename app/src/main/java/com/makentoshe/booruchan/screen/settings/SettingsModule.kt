package com.makentoshe.booruchan.screen.settings

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.settings.controller.*
import com.makentoshe.booruchan.screen.settings.fragment.SettingsPageFragment
import com.makentoshe.booruchan.screen.settings.model.SettingsScreenBuilder
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SettingsModule {

    val module = module {

        factory { SettingsViewPagerController() }

        factory { SettingsTabController() }

        factory { (f: Fragment) ->
            val alertController = NsfwAlertController(f)
            val stateController = NsfwStateController(get(), f.requireContext())
            NsfwSettingController(alertController, stateController)
        }

        factory { SettingsScreenBuilder() }

        factory { SettingsPageController(get()) }
    }
}

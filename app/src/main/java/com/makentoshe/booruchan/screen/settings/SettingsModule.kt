package com.makentoshe.booruchan.screen.settings

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.settings.controller.*
import com.makentoshe.booruchan.screen.settings.model.SettingsScreenBuilder
import org.koin.dsl.module

object SettingsModule {

    val module = module {

        factory { SettingsViewPagerController() }

        factory { SettingsTabController() }

        factory { (f: Fragment) ->
            val alertController = NsfwAlertController(f.requireFragmentManager())
            val stateController = NsfwStateController()
            NsfwSettingController(alertController, stateController)
        }

        factory { SettingsScreenBuilder() }

        factory { SettingsPageController(get()) }
    }
}

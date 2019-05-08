package com.makentoshe.booruchan.screen.settings

import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.screen.settings.controller.*
import com.makentoshe.booruchan.screen.settings.model.SettingsScreenBuilder
import org.koin.dsl.module

object SettingsModule {

    val module = module {

        factory { (fm: FragmentManager) -> SettingsViewPagerController(fm) }

        factory { SettingsTabController() }

        factory { (fm: FragmentManager) ->
            val alertController = NsfwAlertController(fm)
            val stateController = NsfwStateController(get())
            NsfwSettingController(alertController, stateController)
        }

        factory { SettingsScreenBuilder() }

        factory { (p: Int, fm: FragmentManager) -> SettingsPageController(p, fm, get()) }
    }
}

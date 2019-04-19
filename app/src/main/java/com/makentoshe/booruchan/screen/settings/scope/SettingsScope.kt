package com.makentoshe.booruchan.screen.settings.scope

import com.makentoshe.booruchan.screen.settings.fragment.SettingsFragment
import com.makentoshe.booruchan.screen.settings.controller.TabController
import com.makentoshe.booruchan.screen.settings.controller.ViewPagerController
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildSettingsScope() = scope(named<SettingsFragment>()) {
    scoped { ViewPagerController() }
    scoped { TabController() }
}

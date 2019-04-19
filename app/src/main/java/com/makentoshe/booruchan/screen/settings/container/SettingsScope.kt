package com.makentoshe.booruchan.screen.settings.container

import com.makentoshe.booruchan.screen.settings.container.SettingsFragment
import com.makentoshe.booruchan.screen.settings.container.TabController
import com.makentoshe.booruchan.screen.settings.container.ViewPagerController
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildSettingsScope() = scope(named<SettingsFragment>()) {
    scoped { ViewPagerController() }
    scoped { TabController() }
}

package com.makentoshe.booruchan.screen.settings.scope

import com.makentoshe.booruchan.screen.settings.fragment.SettingsPageFragment
import com.makentoshe.booruchan.screen.settings.controller.ContentController
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildSettingsPageScope() = scope(named<SettingsPageFragment>()) {
    scoped { ContentController(get()) }
}

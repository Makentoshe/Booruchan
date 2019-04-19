package com.makentoshe.booruchan.screen.settings.page

import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildSettingsPageScope() = scope(named<SettingsPageFragment>()) {
    scoped { ContentController(get()) }
}

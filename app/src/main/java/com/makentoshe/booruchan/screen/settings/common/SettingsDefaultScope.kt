package com.makentoshe.booruchan.screen.settings.common

import com.makentoshe.booruchan.screen.settings.common.NsfwAlertController
import com.makentoshe.booruchan.screen.settings.common.NsfwSettingController
import com.makentoshe.booruchan.screen.settings.common.NsfwStateController
import com.makentoshe.booruchan.screen.settings.common.SettingsDefaultFragment
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildSettingsDefaultScope() = scope(named<SettingsDefaultFragment>()) {
    scoped { NsfwStateController(get(), get()) }
    scoped { NsfwAlertController() }
    scoped { NsfwSettingController(get(), get()) }
}

package com.makentoshe.booruchan.screen.settings.webm

import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildWebmSettingsScope() = scope(named<SettingsWebmFragment>()) {
    scoped { WebmPlayerStateController(get(), get()) }
    scoped { WebmPlayerSettingController(get()) }
}

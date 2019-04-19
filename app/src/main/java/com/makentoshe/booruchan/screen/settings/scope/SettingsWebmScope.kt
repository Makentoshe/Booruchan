package com.makentoshe.booruchan.screen.settings.scope

import com.makentoshe.booruchan.screen.settings.controller.webm.WebmPlayerSettingController
import com.makentoshe.booruchan.screen.settings.controller.webm.WebmPlayerStateController
import com.makentoshe.booruchan.screen.settings.fragment.SettingsWebmFragment
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildWebmSettingsScope() = scope(named<SettingsWebmFragment>()) {
    scoped { WebmPlayerStateController(get(), get()) }
    scoped { WebmPlayerSettingController(get()) }
}

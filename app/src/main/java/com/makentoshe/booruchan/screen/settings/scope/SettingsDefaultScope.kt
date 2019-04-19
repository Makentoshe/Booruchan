package com.makentoshe.booruchan.screen.settings.scope

import com.makentoshe.booruchan.screen.settings.controller.nsfw.NsfwAlertController
import com.makentoshe.booruchan.screen.settings.controller.nsfw.NsfwSettingController
import com.makentoshe.booruchan.screen.settings.controller.nsfw.NsfwStateController
import com.makentoshe.booruchan.screen.settings.fragment.SettingsDefaultFragment
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildSettingsDefaultScope() = scope(named<SettingsDefaultFragment>()) {
    scoped { NsfwStateController(get(), get()) }
    scoped { NsfwAlertController() }
    scoped { NsfwSettingController(get(), get()) }
}

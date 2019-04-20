package com.makentoshe.booruchan.screen.settings.common

import androidx.fragment.app.Fragment
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

fun Module.buildSettingsDefaultScope() = scope(named<SettingsDefaultFragment>()) {
    scoped { NsfwStateController(get(), get()) }
    scoped { (fragment: Fragment) -> NsfwAlertController(fragment) }
    scoped { (fragment: Fragment) -> NsfwSettingController(get { parametersOf(fragment) }, get()) }
}

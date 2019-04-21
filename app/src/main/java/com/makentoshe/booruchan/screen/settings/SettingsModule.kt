package com.makentoshe.booruchan.screen.settings

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.settings.common.NsfwAlertController
import com.makentoshe.booruchan.screen.settings.common.NsfwSettingController
import com.makentoshe.booruchan.screen.settings.common.NsfwStateController
import com.makentoshe.booruchan.screen.settings.common.SettingsDefaultFragment
import com.makentoshe.booruchan.screen.settings.container.SettingsFragment
import com.makentoshe.booruchan.screen.settings.container.TabController
import com.makentoshe.booruchan.screen.settings.container.ViewPagerController
import com.makentoshe.booruchan.screen.settings.page.ContentController
import com.makentoshe.booruchan.screen.settings.page.SettingsPageFragment
import com.makentoshe.booruchan.screen.settings.webm.SettingsWebmFragment
import com.makentoshe.booruchan.screen.settings.webm.WebmPlayerSettingController
import com.makentoshe.booruchan.screen.settings.webm.WebmPlayerStateController
import org.koin.core.qualifier.named
import org.koin.dsl.module

val settingsModule = module {
    scope(named<SettingsFragment>()) {
        scoped { ViewPagerController() }
        scoped { TabController() }
    }
    scope(named<SettingsDefaultFragment>()) {
        scoped { (fragment: Fragment) -> fragment }
        scoped { NsfwStateController(get(), get()) }
        scoped { NsfwAlertController(get()) }
        scoped { NsfwSettingController(get(), get()) }
    }
    scope(named<SettingsPageFragment>()) {
        scoped { ContentController(get()) }
    }
    scope(named<SettingsWebmFragment>()) {
        scoped { WebmPlayerStateController(get(), get()) }
        scoped { WebmPlayerSettingController(get()) }
    }
}
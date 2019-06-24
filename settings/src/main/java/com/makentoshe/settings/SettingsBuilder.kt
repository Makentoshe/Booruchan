package com.makentoshe.settings

import com.makentoshe.settings.model.realm.RealmBooleanSettingController
import com.makentoshe.settings.screen.controller.NsfwSettingController
import com.makentoshe.settings.screen.controller.NsfwSettingControllerImpl
import io.realm.Realm
import io.realm.RealmConfiguration

/** Abstract factory creates a any types of settings */
interface SettingsBuilder{
    /** Returns a nsfw setting controller */
    fun buildNsfw(): NsfwSettingController
}

/** [SettingsBuilder] interface works under Realm database */
class RealmSettingsBuilder(private val configuration: RealmConfiguration): SettingsBuilder {
    /** Returns a nsfw setting controller based on Realm database */
    override fun buildNsfw(): NsfwSettingController {
        return NsfwSettingControllerImpl(RealmBooleanSettingController(configuration))
    }
}

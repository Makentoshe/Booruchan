package com.makentoshe.settings.common

import com.makentoshe.settings.realm.RealmBooleanSettingController
import io.realm.RealmConfiguration

/** Abstract factory creates a any types of settings */
interface SettingsBuilder {
    /** Returns a nsfw setting controller */
    fun buildNsfw(): NsfwSettingController
    /** Returns a nsfw alert setting controller */
    fun buildNsfwAlert(): NsfwAlertSettingController
}

/** [SettingsBuilder] interface works under Realm database */
class RealmSettingsBuilder(private val configuration: RealmConfiguration) : SettingsBuilder {
    /** Returns a nsfw setting controller based on Realm database */
    override fun buildNsfw(): NsfwSettingController {
        return NsfwSettingControllerImpl(RealmBooleanSettingController(configuration))
    }
    /** Returns a nsfw alert setting controller based on Realm database */
    override fun buildNsfwAlert(): NsfwAlertSettingController {
        return NsfwAlertSettingControllerImpl(RealmBooleanSettingController(configuration))
    }
}

package com.makentoshe.settings

import com.makentoshe.settings.model.realm.RealmBooleanSettingController
import com.makentoshe.settings.screen.controller.NsfwSettingController
import com.makentoshe.settings.screen.controller.NsfwSettingControllerImpl
import io.realm.Realm
import io.realm.RealmConfiguration

/** Creates concrete settings controllers */
class SettingsBuilder(private val configuration: RealmConfiguration = Realm.getDefaultConfiguration()!!) {

    /** Builds a [NsfwSettingController] instance */
    fun buildNsfw(): NsfwSettingController {
        return NsfwSettingControllerImpl(RealmBooleanSettingController(configuration))
    }
}
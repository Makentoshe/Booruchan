package com.makentoshe.settings.screen.controller

import com.makentoshe.settings.model.SettingController

interface NsfwSettingController {
    var value: Boolean
}

/**
 * Performs controlling NsfwSetting using [settingController].
 */
class NsfwSettingControllerImpl(private val settingController: SettingController<Boolean>) : NsfwSettingController {

    private val settingsTitle = "NsfwSettings"

    override var value: Boolean
        get() = settingController.get(settingsTitle, false)
        set(value) = settingController.put(settingsTitle, value)
}

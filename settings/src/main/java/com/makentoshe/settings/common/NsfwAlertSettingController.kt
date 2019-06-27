package com.makentoshe.settings.common

import com.makentoshe.settings.SettingController

interface NsfwAlertSettingController {
    var value: Boolean
}

/**
 * Perform controlling alert nsfw displaying using [settingController].
 */
class NsfwAlertSettingControllerImpl(
    private val settingController: SettingController<Boolean>
) : NsfwAlertSettingController {

    private val settingTitle = "AlertNsfwSetting"

    override var value: Boolean
        get() = settingController.get(settingTitle, true)
        set(value) = settingController.put(settingTitle, value)
}

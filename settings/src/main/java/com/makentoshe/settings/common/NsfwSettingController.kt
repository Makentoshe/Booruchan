package com.makentoshe.settings.common

import com.makentoshe.settings.SettingController

/** Interface for nsfw setting */
interface NsfwSettingController {
    /** Nsfw setting value */
    var value: Boolean
}

/**
 * Performs controlling NsfwSetting using [settingController].
 */
class NsfwSettingControllerImpl(private val settingController: SettingController<Boolean>) : NsfwSettingController {

    /** Setting title used for searching in local storage */
    private val settingsTitle = "NsfwSetting"

    /** Current setting value */
    override var value: Boolean
        get() = settingController.get(settingsTitle, false)
        set(value) = settingController.put(settingsTitle, value)
}

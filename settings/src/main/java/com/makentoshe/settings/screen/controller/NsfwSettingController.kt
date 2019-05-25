package com.makentoshe.settings.screen.controller

import com.makentoshe.settings.model.SettingController

/**
 * Performs controlling NsfwSetting using [settingController].
 */
class NsfwSettingController(private val settingController: SettingController<Boolean>) {

    private val settingTitle = "NsfwSetting"

    fun set(value: Boolean) {
        settingController.put(settingTitle, value)
    }

    fun get(): Boolean {
        return settingController.get(settingTitle, false)
    }

    class Factory {
        fun build(settingController: SettingController<Boolean>): NsfwSettingController {
            return NsfwSettingController(settingController)
        }
    }
}
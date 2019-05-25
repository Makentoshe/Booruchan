package com.makentoshe.settings.screen.controller

import com.makentoshe.settings.model.SettingController

/**
 * Perform controlling alert nsfw displaying using [settingController].
 */
class NsfwAlertSettingController(private val settingController: SettingController<Boolean>) {
    private val settingTitle = "AlertNsfwSetting"

    fun set(value: Boolean) {
        settingController.put(settingTitle, value)
    }

    fun get(): Boolean {
        return settingController.get(settingTitle, true)
    }

    class Factory {
        fun build(settingController: SettingController<Boolean>): NsfwAlertSettingController {
            return NsfwAlertSettingController(settingController)
        }
    }

}
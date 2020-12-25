package com.makentoshe.booruchan.screen.settings.webmscreen

import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.settings.model.SettingViewChecker

class SettingViewCheckerWebmPlaying(root: View, trigger: CheckBox) : SettingViewChecker(root, trigger) {

    override fun setDefaultSetting(context: Context) {
        trigger.isChecked = AppSettings.getWebmPlayingOnPlace(context)
    }

    override fun onStateChanged(context: Context, newState: Boolean) {
        AppSettings.setWebmPlayingOnPlace(context, newState)
    }

}
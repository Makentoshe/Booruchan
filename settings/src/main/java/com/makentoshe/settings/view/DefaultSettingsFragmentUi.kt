package com.makentoshe.settings.view

import android.content.Context
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.verticalLayout

class DefaultSettingsFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            createNsfwSetting()
        }
    }

    private fun _LinearLayout.createNsfwSetting() {
        val mainId = com.makentoshe.settings.R.id.nsfw_setting
        val targetId = com.makentoshe.settings.R.id.nsfw_setting_target
        val textRes = com.makentoshe.settings.R.string.nsfw_setting_title
        BooleanSettingUi(mainId, targetId, textRes)
            .createView(AnkoContext.createDelegate(this))
    }
}
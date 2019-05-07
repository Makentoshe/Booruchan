package com.makentoshe.booruchan.screen.settings.view

import android.content.Context
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SettingsDefaultUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        themedLinearLayout(style.default) {
            orientation = LinearLayout.VERTICAL
            lparams(matchParent, matchParent)
            createNsfwSetting()
        }
    }

    private fun _LinearLayout.createNsfwSetting() {
        val id = R.id.setting_nsfw
        val checkbox = R.id.setting_nsfw_checkbox
        val text = R.string.enable_nsfw
        CheckboxSettingUi(id, checkbox, text)
            .createView(AnkoContext.createDelegate(this))
    }
}
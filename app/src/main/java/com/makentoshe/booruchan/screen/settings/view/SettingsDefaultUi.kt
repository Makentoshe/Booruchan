package com.makentoshe.booruchan.screen.settings.view

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.settings.model.SettingWithCheckUi
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SettingsDefaultUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View =
        with(ui) {
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
        SettingWithCheckUi(id, checkbox, text)
            .createView(AnkoContext.createDelegate(this))
    }
}
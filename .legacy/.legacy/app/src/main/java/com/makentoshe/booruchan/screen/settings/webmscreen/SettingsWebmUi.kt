package com.makentoshe.booruchan.screen.settings.webmscreen

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.settings.view.SettingWithCheckUi
import org.jetbrains.anko.*

class SettingsWebmUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            lparams(matchParent, matchParent)

            createWebmOnPlaceSetting()
        }
    }

    private fun _LinearLayout.createWebmOnPlaceSetting() {
        val id = R.id.setting_webm_on_place
        val checkbox = R.id.setting_webm_on_place_checkbox
        val text = R.string.enable_webm_playing_on_place
        SettingWithCheckUi(id, checkbox, text)
            .createView(AnkoContext.createDelegate(this))
    }
}
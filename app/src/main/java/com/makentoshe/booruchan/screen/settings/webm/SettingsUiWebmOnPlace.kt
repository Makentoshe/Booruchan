package com.makentoshe.booruchan.screen.settings.webm

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SettingsUiWebmOnPlace: AnkoComponent<_LinearLayout> {
    override fun createView(ui: AnkoContext<_LinearLayout>): View = with(ui.owner) {
        relativeLayout {
            id = R.id.setting_webm_on_place
            themedTextView(style.default) {
                textResource = R.string.enable_webm_playing_on_place
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dip(8), 0, 0, 0)
            }.lparams(matchParent, matchParent) {
                startOf(R.id.setting_webm_on_place_checkbox)
            }
            checkBox {
                id = R.id.setting_webm_on_place_checkbox
                gravity = Gravity.CENTER
            }.lparams(height = matchParent) {
                marginEnd = dip(16)
                alignParentRight()
            }
        }.lparams(matchParent, dip(56))
    }
}
package com.makentoshe.booruchan.screen.settings

import android.view.Gravity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SettingsUiNSFW : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        linearLayout {
            id = R.id.setting_nsfw
            themedTextView(style.default) {
                textResource = R.string.enable_nsfw
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dip(8), 0, 0, 0)
            }.lparams(height = matchParent) {
                weight = 9f
            }
            checkBox {
                id = R.id.setting_nsfw_checkbox
                gravity = Gravity.CENTER
            }.lparams(height = matchParent) {
                weight = 1f
            }
        }
    }
}
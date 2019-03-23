package com.makentoshe.booruchan.screen.settings

import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SettingsUiToolbar : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        themedRelativeLayout(style.toolbar) {
            id = R.id.settings_toolbar
            themedToolbar(style.toolbar) {
                titleResource = R.string.app_settings
            }.lparams(matchParent, matchParent)
        }.lparams(matchParent, dip(56)) {
            alignParentTop()
        }
    }
}
package com.makentoshe.settings.view

import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class SettingsFragmentUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        viewPager {
            id = com.makentoshe.settings.R.id.settings_viewpager
        }.lparams(matchParent, matchParent) {
            below(com.makentoshe.settings.R.id.settings_tab)
        }
    }
}
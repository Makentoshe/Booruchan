package com.makentoshe.booruchan.screen.settings.container

import android.view.View
import android.view.ViewManager
import com.google.android.material.tabs.TabLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SettingsUiTab : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        themedTabLayout(style.toolbar) {
            id = R.id.settings_tab
        }.lparams(matchParent, dip(56 / 2)) {
            below(R.id.settings_toolbar)
        }
    }

    private fun ViewManager.themedTabLayout(theme: Int, init: TabLayout.() -> Unit): TabLayout {
        return ankoView({ TabLayout(it) }, theme, init)
    }
}
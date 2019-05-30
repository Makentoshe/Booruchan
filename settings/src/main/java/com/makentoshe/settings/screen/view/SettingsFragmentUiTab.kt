package com.makentoshe.settings.screen.view

import android.view.ViewManager
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SettingsFragmentUiTab : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        val style = attr(com.makentoshe.style.R.attr.toolbar_style).data
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)
        themedTabLayout(style) {
            id = com.makentoshe.settings.R.id.settings_tab
        }.lparams(matchParent, height / 2) {
            below(com.makentoshe.settings.R.id.settings_toolbar)
        }
    }

    private fun ViewManager.themedTabLayout(theme: Int, init: TabLayout.() -> Unit): TabLayout {
        return ankoView({ TabLayout(it) }, theme, init)
    }
}
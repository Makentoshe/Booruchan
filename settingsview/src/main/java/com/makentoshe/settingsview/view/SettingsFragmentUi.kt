package com.makentoshe.settingsview.view

import android.content.Context
import android.view.ViewManager
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.viewPager

/**
 * File contains a [SettingsFragment] user interface.
 */
class SettingsFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        relativeLayout {
            SettingsFragmentUiToolbar().createView(AnkoContext.createDelegate(this))
            SettingsFragmentUiTab().createView(AnkoContext.createDelegate(this))
            SettingsFragmentUiContent().createView(AnkoContext.createDelegate(this))
        }
    }
}

class SettingsFragmentUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        viewPager {
            id = com.makentoshe.settingsview.R.id.settings_viewpager
        }.lparams(matchParent, matchParent) {
            below(com.makentoshe.settingsview.R.id.settings_tab)
        }
    }
}

class SettingsFragmentUiTab : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        val style = attr(com.makentoshe.style.R.attr.toolbar_style).data
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)
        themedTabLayout(style) {
            id = com.makentoshe.settingsview.R.id.settings_tab
        }.lparams(matchParent, height / 2) {
            below(com.makentoshe.settingsview.R.id.settings_toolbar)
        }
    }

    private fun ViewManager.themedTabLayout(theme: Int, init: TabLayout.() -> Unit): TabLayout {
        return ankoView({ TabLayout(it) }, theme, init)
    }
}

class SettingsFragmentUiToolbar : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        val style = attr(com.makentoshe.style.R.attr.toolbar_style).data
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)
        themedRelativeLayout(style) {
            id = com.makentoshe.settingsview.R.id.settings_toolbar
            themedToolbar(style) {
                titleResource = com.makentoshe.settingsview.R.string.title
            }.lparams(matchParent, matchParent)
        }.lparams(matchParent, height) {
            alignParentTop()
        }
    }
}

package com.makentoshe.settings.screen.view

import android.content.Context
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

class SettingsFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        relativeLayout {
            SettingsFragmentUiToolbar()
                .createView(AnkoContext.createDelegate(this))
            SettingsFragmentUiTab()
                .createView(AnkoContext.createDelegate(this))
            SettingsFragmentUiContent()
                .createView(AnkoContext.createDelegate(this))
        }
    }
}
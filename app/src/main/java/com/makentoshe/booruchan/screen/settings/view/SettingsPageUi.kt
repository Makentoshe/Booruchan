package com.makentoshe.booruchan.screen.settings.view

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout

class SettingsPageUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        frameLayout {
            id = R.id.settings_container
        }
    }
}
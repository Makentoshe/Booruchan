package com.makentoshe.booruchan.screen.settings.view

import android.content.Context
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.themedViewPager

class SettingsUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        themedRelativeLayout(style.default) {
            setOnClickListener { /* just handle on click event to avoid actions in background fragment*/ }
            SettingsUiToolbar().createView(AnkoContext.createDelegate(this))
            SettingsUiTab().createView(AnkoContext.createDelegate(this))

            themedViewPager(style.default) {
                id = R.id.settings_viewpager
            }.lparams(matchParent, matchParent) {
                below(R.id.settings_tab)
            }
        }
    }
}
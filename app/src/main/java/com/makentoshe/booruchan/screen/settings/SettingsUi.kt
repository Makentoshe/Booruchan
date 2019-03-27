package com.makentoshe.booruchan.screen.settings

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SettingsUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        themedRelativeLayout(style.default) {
            setOnClickListener { /* just handle on click event to avoid actions in background fragment*/ }
            SettingsUiToolbar()
                .createView(AnkoContext.createDelegate(this))
            SettingsUiNSFW()
                .createView(AnkoContext.createDelegate(this)).lparams(matchParent, dip(56)) {
                below(R.id.settings_toolbar)
            }
        }
    }
}
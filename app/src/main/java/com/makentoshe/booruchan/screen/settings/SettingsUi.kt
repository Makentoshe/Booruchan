package com.makentoshe.booruchan.screen.settings

import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SettingsUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        themedRelativeLayout(style.default) {
            setOnClickListener { /* just handle on click event to avoid actions in background fragment*/ }
            SettingsUiToolbar().createView(AnkoContext.createDelegate(this))

            linearLayout {
                orientation = LinearLayout.VERTICAL
                SettingsUiNSFW().createView(AnkoContext.createDelegate(this))
                SettingsUiStreamingDownload().createView(AnkoContext.createDelegate(this))
            }.lparams(matchParent, matchParent) {
                below(R.id.settings_toolbar)
            }
        }
    }
}
package com.makentoshe.booruchan.screen.settings.view

import android.view.Gravity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SettingsUiNSFW : AnkoComponent<_LinearLayout> {
    override fun createView(ui: AnkoContext<_LinearLayout>) = with(ui.owner) {
        relativeLayout {
            id = R.id.setting_nsfw
            themedTextView(style.default) {
                textResource = R.string.enable_nsfw
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dip(8), 0, 0, 0)
            }.lparams(matchParent, matchParent) {
                startOf(R.id.setting_stream_download_checkbox)
            }
            checkBox {
                id = R.id.setting_nsfw_checkbox
                gravity = Gravity.CENTER
            }.lparams(height = matchParent) {
                marginEnd = dip(16)
                alignParentRight()
            }
        }.lparams(matchParent, dip(56))
    }
}
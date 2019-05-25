package com.makentoshe.settings.screen.view

import com.makentoshe.style.styleAttr
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.appcompat.v7.titleResource

class SettingsFragmentUiToolbar : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        val style = styleAttr(com.makentoshe.style.R.attr.toolbar_style).data
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)
        themedRelativeLayout(style) {
            id = com.makentoshe.settings.R.id.settings_toolbar
            themedToolbar(style) {
                titleResource = com.makentoshe.settings.R.string.title
            }.lparams(matchParent, matchParent)
        }.lparams(matchParent, height) {
            alignParentTop()
        }
    }
}
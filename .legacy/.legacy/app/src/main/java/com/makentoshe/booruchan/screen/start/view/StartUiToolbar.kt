package com.makentoshe.booruchan.screen.start.view

import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*


class StartUiToolbar : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        themedRelativeLayout(style.toolbar) {
            id = R.id.start_toolbar
            elevation = dip(10).toFloat()
            themedToolbar(style.toolbar) {
                titleResource = R.string.app_name
            }.lparams(width = matchParent) { alignWithParent = true }

            StartUiToolbarOverflow().createView(AnkoContext.createDelegate(this))
        }
    }
}
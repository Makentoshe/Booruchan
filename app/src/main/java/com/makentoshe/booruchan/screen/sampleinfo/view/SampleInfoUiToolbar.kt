package com.makentoshe.booruchan.screen.sampleinfo.view

import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SampleInfoUiToolbar : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        themedRelativeLayout(style.toolbar) {
            id = R.id.sampleinfo_toolbar

            SampleInfoUiToolbarConstr(
                R.id.sampleinfo_toolbar_info,
                R.string.info
            )
                .createView(AnkoContext.createDelegate(this))

            SampleInfoUiToolbarConstr(
                R.id.sampleinfo_toolbar_tags,
                R.string.tags
            )
                .createView(AnkoContext.createDelegate(this))

            SampleInfoUiToolbarConstr(
                R.id.sampleinfo_toolbar_comments,
                R.string.comments
            )
                .createView(AnkoContext.createDelegate(this))

        }.lparams(matchParent, dip(56)) {
            alignParentTop()
        }
    }
}
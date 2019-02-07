package com.makentoshe.booruchan.postsamples.view

import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostSamplesUiToolbar(
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        relativeLayout {
            id = R.id.postsamples_toolbar
            backgroundColorResource = style.toolbar.primaryColorRes
        }.lparams(matchParent, dip(56))
    }
}
package com.makentoshe.booruchan.screen.sampleinfo.view

import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.style.getColorFromStyle
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

            createTagsToolbar()

            SampleInfoUiToolbarConstr(
                R.id.sampleinfo_toolbar_comments,
                R.string.comments
            )
                .createView(AnkoContext.createDelegate(this))

        }.lparams(matchParent, dip(56)) {
            alignParentTop()
        }
    }

    private fun _RelativeLayout.createTagsToolbar() {
        SampleInfoUiToolbarConstr(
            R.id.sampleinfo_toolbar_tags,
            R.string.tags
        ).createView(AnkoContext.createDelegate(this)).apply {
            createSearchIcon().visibility = View.GONE
        }
    }

    private fun _RelativeLayout.createSearchIcon() = themedFrameLayout(style.toolbar) {
        id = R.id.sampleinfo_toolbar_tags_search
        lparams(dip(56), dip(56))

        themedImageView(style.toolbar) {
            setImageResource(R.drawable.avd_cross_magnify)
            setColorFilter(getColorFromStyle(android.R.attr.textColorPrimary), PorterDuff.Mode.SRC_ATOP)
        }.lparams(dip(24), dip(24)) {
            gravity = Gravity.CENTER
        }
    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }
}
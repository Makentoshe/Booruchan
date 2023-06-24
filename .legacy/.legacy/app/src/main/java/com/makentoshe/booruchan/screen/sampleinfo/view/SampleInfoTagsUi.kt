package com.makentoshe.booruchan.screen.sampleinfo.view

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.view.chipGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.scrollView

class SampleInfoTagsUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        scrollView {
            chipGroup {
                id = R.id.sampleinfo_tags_chipgroup
            }.lparams {
                setMargins(dip(8), 0, dip(8), 0)
            }
        }
    }
}
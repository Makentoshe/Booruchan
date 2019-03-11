package com.makentoshe.booruchan.screen.sampleinfo

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.chipGroup
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
                setMargins(dip(8), dip(10), dip(8), dip(8))
            }
        }
    }
}
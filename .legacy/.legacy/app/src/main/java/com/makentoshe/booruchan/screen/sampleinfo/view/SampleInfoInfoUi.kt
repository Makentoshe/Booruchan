package com.makentoshe.booruchan.screen.sampleinfo.view

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.listView

class SampleInfoInfoUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        listView {
            id = R.id.sampleinfo_info_listview
        }
    }
}
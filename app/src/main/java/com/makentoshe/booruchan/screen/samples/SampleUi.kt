package com.makentoshe.booruchan.screen.samples

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.viewPager

class SampleUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        viewPager {
            backgroundColor = Color.CYAN
            id = R.id.samples_container_viewpager
        }
    }
}
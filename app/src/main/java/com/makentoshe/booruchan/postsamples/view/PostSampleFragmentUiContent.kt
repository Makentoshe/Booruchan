package com.makentoshe.booruchan.postsamples.view

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class PostSampleFragmentUiContent(
    private val viewModel: PostsSampleFragmentViewModel
) : AnkoComponent<LinearLayout> {
    override fun createView(ui: AnkoContext<LinearLayout>): View = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            viewPager {
                backgroundColor = Color.CYAN
//                adapter = viewModel.getPagerAdapter()
            }
        }
    }
}
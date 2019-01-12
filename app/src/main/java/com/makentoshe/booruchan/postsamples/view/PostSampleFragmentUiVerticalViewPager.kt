package com.makentoshe.booruchan.postsamples.view

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.makentoshe.booruchan.VerticalViewPager
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.frameLayout

class PostSampleFragmentUiVerticalViewPager(
    private val viewModel: PostsSampleFragmentViewModel
) : AnkoComponent<LinearLayout>{

    override fun createView(ui: AnkoContext<LinearLayout>): View = with(ui) {
        frameLayout {
            addView(VerticalViewPager(context).apply {
                backgroundColor = Color.MAGENTA
            })
        }
    }
}
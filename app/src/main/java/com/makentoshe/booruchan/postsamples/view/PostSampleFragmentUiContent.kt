package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.BlockableViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import com.makentoshe.booruchan.postsamples.model.SamplePageController
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

class PostSampleFragmentUiContent(
    private val viewModel: PostsSampleFragmentViewModel,
    private val fragmentManager: FragmentManager
) : AnkoComponent<LinearLayout> {
    override fun createView(ui: AnkoContext<LinearLayout>): View = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            addView(BlockableViewPager(context).apply {
                id = R.id.viewpager
                adapter = viewModel.getPagerAdapter(fragmentManager)
                if (currentItem == 0) currentItem = viewModel.startPosition
                viewModel.blockController.subscribe {
                    when (it) {
                        SamplePageController.Command.BLOCK -> {
                            isBlocked = true
                        }
                        SamplePageController.Command.UNBLOCK -> {
                            isBlocked = false
                        }
                        else -> viewModel.backToPreviews()
                    }
                }
            })
        }
    }
}
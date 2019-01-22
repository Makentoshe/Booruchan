package com.makentoshe.booruchan.postsamplespage

import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.VerticalViewPager
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.onPageChangeListener

class PostSamplePageFragmentUi(
    private val viewModel: PostSamplePageViewModel
) : AnkoComponent<PostSamplePageFragment> {

    private lateinit var root: View

    override fun createView(ui: AnkoContext<PostSamplePageFragment>): View = with(ui) {
        frameLayout {
            root = this
            lparams(matchParent, matchParent)
            addView(VerticalViewPager(context).apply {
                id = R.id.content_viewpager
                adapter = viewModel.getViewPagerAdapter(ui.owner.childFragmentManager)
                currentItem = 1
                onPageChangeListener {
                    onPageSelected {
                        when (it) {
                            1 -> {
                                viewModel.unblock()
                                println("Can swap")
                            }
                            2 -> {
                                viewModel.block()
                                println("Cant swap")
                            }
                        }
                    }
                    val parentFragment = ui.owner.parentFragment
                    onPageScrolled { position, positionOffset, positionOffsetPixels ->
                        if (position == 0) {
                            parentFragment?.view?.alpha = positionOffset
                            if (positionOffset == 0f) viewModel.backToPreviews()
                        }
                    }
                }
            })
        }
    }
}
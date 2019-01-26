package com.makentoshe.booruchan.postsamplespage

import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.annotation.Px
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

    override fun createView(ui: AnkoContext<PostSamplePageFragment>): View = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            verticalViewPager {
                id = R.id.content_viewpager
                adapter = viewModel.getViewPagerAdapter(ui.owner.childFragmentManager)
                currentItem = 1

                val root = ui.owner.parentFragment!!.view!!
                onPageChange(root)
                onPageSelected(::pageSelected)
            }
        }
    }

    private fun ViewGroup.verticalViewPager(action: VerticalViewPager.() -> Unit) {
        addView(VerticalViewPager(context).apply(action))
    }

    private fun VerticalViewPager.onPageSelected(action: (Int) -> Unit) {
        onPageChangeListener {
            onPageSelected(action)
        }
    }

    private fun VerticalViewPager.onPageChange(root: View) {
        onPageChangeListener {
            onPageScrolled { position, offset, offsetPx ->
                if (position == 0) root.alpha = offset
            }
        }
    }

    private fun pageSelected(page: Int) {
        when (page) {
            0 -> viewModel.backToPreviews()
            //can swap
            1 -> viewModel.unblock()
            //can't swap
            2 -> viewModel.block()
        }
    }
}
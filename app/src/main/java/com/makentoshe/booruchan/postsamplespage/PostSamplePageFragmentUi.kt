package com.makentoshe.booruchan.postsamplespage

import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
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
            verticalViewPager {
                id = R.id.content_viewpager
                adapter = viewModel.getViewPagerAdapter(ui.owner.childFragmentManager)
                onPageSelected(::pageSelected)
            }
        }
    }

    private fun ViewGroup.verticalViewPager(action: VerticalViewPager.() -> Unit) {
        addView(VerticalViewPager(context).apply(action))
    }

    private fun VerticalViewPager.onPageSelected(action: (Int) -> Unit) {
        onPageChangeListener { onPageSelected(action) }
    }

    private fun pageSelected(page: Int) {
        when (page) {
            //can swap
            0 -> viewModel.unblock()
            //can't swap
            1 -> viewModel.block()
        }
    }
}
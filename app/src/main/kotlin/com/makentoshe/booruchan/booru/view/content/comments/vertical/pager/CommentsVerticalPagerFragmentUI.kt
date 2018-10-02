package com.makentoshe.booruchan.booru.view.content.comments.vertical.pager

import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import com.makentoshe.booruchan.booru.model.content.comments.pager.vertical.CommentsContentVerticalPagerAdapter
import com.makentoshe.booruchan.booru.model.content.comments.pager.vertical.CommentsContentVerticalPagerViewModel
import com.makentoshe.booruchan.common.view.verticalViewPager
import org.jetbrains.anko.support.v4.onPageChangeListener

class CommentsVerticalPagerFragmentUI(private val viewModel: CommentsContentVerticalPagerViewModel)
    : AnkoComponent<CommentsVerticalPagerFragment> {

    override fun createView(ui: AnkoContext<CommentsVerticalPagerFragment>): View = with(ui) {
        verticalViewPager {
            id = Id.viewPager
            adapter = CommentsContentVerticalPagerAdapter(ui.owner.childFragmentManager)
            onPageChangeListener {
                onPageSelected { println(it) }
            }
        }
    }

    object Id {
        const val viewPager = 1
    }

}
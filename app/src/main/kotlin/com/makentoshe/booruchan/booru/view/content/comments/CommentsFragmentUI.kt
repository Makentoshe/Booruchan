package com.makentoshe.booruchan.booru.view.content.comments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.makentoshe.booruchan.booru.model.content.comments.CommentsContentViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import android.support.v4.app.FragmentPagerAdapter
import com.makentoshe.booruchan.common.view.verticalViewPager
import org.jetbrains.anko.support.v4.onPageChangeListener


class CommentsFragmentUI(private val viewModel: CommentsContentViewModel)
    : AnkoComponent<CommentsFragment> {

    override fun createView(ui: AnkoContext<CommentsFragment>): View = with(ui) {
        verticalViewPager {
            id = Id.viewPager
            adapter = MyFragmentPagerAdapter(ui.owner.childFragmentManager)
            onPageChangeListener {
                onPageSelected { println(it) }
            }
        }
    }

    object Id {
        const val viewPager = 1
    }

}

private class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return CommentPageFragment.new(position)
    }

    override fun getCount(): Int {
        return 10
    }

}
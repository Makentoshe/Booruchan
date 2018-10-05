package com.makentoshe.booruchan.booru.model.content.comments.pager.vertical

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.booru.view.content.comments.vertical.pager.CommentPageFragment


class CommentsContentVerticalPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return CommentPageFragment.new(position)
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

}
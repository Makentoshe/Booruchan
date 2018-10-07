package com.makentoshe.booruchan.booru.content.comments.vertical.pager.model

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.booru.content.comments.vertical.pager.view.PageFragment


class CommentsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return PageFragment.new(position)
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

}
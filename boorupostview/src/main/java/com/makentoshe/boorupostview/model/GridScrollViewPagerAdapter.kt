package com.makentoshe.boorupostview.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.PostsFragmentNavigator
import com.makentoshe.boorupostview.fragment.GridScrollElementFragment

/**
 * Adapter for a view pager, creates a fragments contains a grid view for a posts displaying
 */
class GridScrollViewPagerAdapter(
    private val booru: Booru,
    private val tags: Set<Tag>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return GridScrollElementFragment.build(booru, tags, position)
    }

    override fun getCount() = Int.MAX_VALUE

    class Builder(
        private val fragmentManager: FragmentManager,
        private val booru: Booru
    ) {

        fun build(tags: Set<Tag>): GridScrollViewPagerAdapter {
            return GridScrollViewPagerAdapter(booru, tags, fragmentManager)
        }
    }
}
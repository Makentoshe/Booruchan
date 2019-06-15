package com.makentoshe.boorupostview.presenter

import androidx.viewpager.widget.ViewPager

/**
 * Presenter component for a grid scroll screen. User interface should contains a view pager.
 */
interface PostsGridScrollFragmentPresenter {

    /** Bind a [ViewPager]. */
    fun bindViewPager(view: ViewPager)
}
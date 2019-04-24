package com.makentoshe.booruchan.screen.posts.container.controller

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.screen.posts.container.model.PostsViewPagerAdapter
import org.jetbrains.anko.find

class ViewPagerController(
    private val booruHolder: BooruHolder,
    private val searchController: SearchController,
    private val fragmentManager: FragmentManager
) {

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)

        //set up the horizontal view pager for displaying the pages with the images
        //on each starting search the old adapter will be replaced by new one with
        //tags for current search
        searchController.onSearchStarted { tags ->
            viewpager.adapter = PostsViewPagerAdapter(fragmentManager, tags, booruHolder.booru)
        }
    }
}
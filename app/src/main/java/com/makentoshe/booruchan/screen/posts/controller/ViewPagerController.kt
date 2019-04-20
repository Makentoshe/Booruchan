package com.makentoshe.booruchan.screen.posts.controller

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.screen.posts.viewmodel.SearchState
import com.makentoshe.booruchan.screen.posts.model.PostsViewPagerAdapter
import org.jetbrains.anko.find

class ViewPagerController(
    private val booru: Booru,
    private val searchState: SearchState,
    private val fragmentManager: FragmentManager
) {

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)

        //set up the horizontal view pager for displaying the pages with the images
        //on each starting search the old adapter will be replaced by new one with
        //tags for current search
        searchState.onSearchStarted { tags ->
            viewpager.adapter =
                PostsViewPagerAdapter(fragmentManager, tags, booru)
        }
    }
}
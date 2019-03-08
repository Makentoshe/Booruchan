package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import com.makentoshe.booruchan.screen.posts.PostsViewPagerAdapter
import com.makentoshe.booruchan.screen.posts.model.SearchController
import org.jetbrains.anko.find

class PostsUiContentInflator(
    private val searchController: SearchController,
    private val fragmentManager: FragmentManager,
    private val booru: Booru
    ): Inflator {
    override fun inflate(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)
        searchController.onSearchStarted{
            viewpager.adapter = PostsViewPagerAdapter(fragmentManager, it, booru)
        }
    }
}
package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import org.jetbrains.anko.find

class PostsUiContentInflator(
    private val fragmentManager: FragmentManager,
    private val booru: Booru
) : Inflator {
    override fun inflate(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)
//        searchController.onSearchStarted {
//            GlobalScope.launch {
//                PostInternalCache(view.context.applicationContext).clear()
//            }
//            viewpager.adapter = PostsViewPagerAdapter(fragmentManager, it, booru)
//        }
    }
}
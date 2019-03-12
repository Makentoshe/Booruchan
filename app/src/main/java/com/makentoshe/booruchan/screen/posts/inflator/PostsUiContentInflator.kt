package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import androidx.core.util.Consumer
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.R
import org.jetbrains.anko.find

class PostsUiContentInflator(
    private val fragmentManager: FragmentManager,
    private val booru: Booru
) : Consumer<View> {
    override fun accept(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)
//        searchController.onSearchStarted {
//            GlobalScope.launch {
//                PostInternalCache(view.context.applicationContext).clear()
//            }
//            viewpager.adapter = PostsViewPagerAdapter(fragmentManager, it, booru)
//        }
    }
}
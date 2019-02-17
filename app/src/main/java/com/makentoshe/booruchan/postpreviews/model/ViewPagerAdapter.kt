package com.makentoshe.booruchan.postpreviews.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.postpreview.PostPageScreen

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val tags: Set<Tag>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = PostPageScreen(position, booru, tags).fragment

    override fun getCount() = Int.MAX_VALUE
}
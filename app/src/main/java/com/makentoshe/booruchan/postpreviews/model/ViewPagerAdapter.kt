package com.makentoshe.booruchan.postpreviews.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.postpreview.PostPageFragment
import com.makentoshe.booruchan.postpreview.PostPageScreen

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val arguments: PostPageFragment.Arguments
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = PostPageScreen(arguments, position).fragment

    override fun getCount() = Int.MAX_VALUE
}
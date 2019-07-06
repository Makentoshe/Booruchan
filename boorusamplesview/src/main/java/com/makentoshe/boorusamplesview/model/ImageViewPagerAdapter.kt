package com.makentoshe.boorusamplesview.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.boorusamplesview.ImageViewPagerElementFragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag

/**
 * [androidx.viewpager.widget.ViewPager] adapter displays a pages with image views.
 *
 * @param fragmentManager attaches a [Fragment]s to a [androidx.viewpager.widget.ViewPager].
 * @param booru BooruAPI instance used for requests
 * @param tags is a [Tag] container may use for requests
 */
class ImageViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val tags: Set<Tag>
) : FragmentStatePagerAdapter(fragmentManager) {

    /** Returns a [ImageViewPagerElementFragment] instance for current [position] */
    override fun getItem(position: Int): Fragment {
        return ImageViewPagerElementFragment.build(position, booru, tags)
    }

    /** Maximal pages count in adapter. Used [Int.MAX_VALUE] for infinite scroll emulation */
    override fun getCount() = Int.MAX_VALUE
}

package com.makentoshe.booruchan.postsamples.model

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostSamplePageInfoScreen
import com.makentoshe.booruchan.PostSamplePagePreviewScreen
import com.makentoshe.booruchan.PostsRepository

class SamplePageContentPagerAdapter(
    fragmentManager: FragmentManager,
    private val sampleRepository: ImageRepository,
    private val position: Int,
    private val postsRepository: PostsRepository
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> PostSamplePagePreviewScreen(sampleRepository, this.position, postsRepository).fragment
            2 -> PostSamplePageInfoScreen(this.position, postsRepository).fragment
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount() = 3

    override fun saveState(): Parcelable {
        val bundle = super.saveState() as Bundle
        bundle.putParcelableArray("states", null) // Never maintain any states from the base class, just null it out
        return bundle
    }
}
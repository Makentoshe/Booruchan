package com.makentoshe.booruchan.postsamples.model

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.PostSamplePageScreen
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository

class SamplePagePagerAdapter(
    fragmentManager: FragmentManager,
    private val samplePageHorizontalScrollController: SamplePageHorizontalScrollBlockController,
    private val sampleRepository: ImageRepository,
    private val postsRepository: PostsRepository
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) =
        PostSamplePageScreen(position, samplePageHorizontalScrollController, sampleRepository, postsRepository).fragment

    override fun getCount() = Int.MAX_VALUE

    override fun saveState(): Parcelable {
        val bundle = super.saveState() as Bundle
        bundle.putParcelableArray("states", null) // Never maintain any states from the base class, just null it out
        return bundle
    }
}
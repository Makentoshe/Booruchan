package com.makentoshe.booruchan.postsamples.model

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.postsample.PostSampleScreen
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository

class SamplesContentViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val postsRepository: PostsRepository,
    private val samplesRepository: SampleImageRepository
): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PostSampleScreen(position, postsRepository, samplesRepository).fragment
    }

    override fun getCount() = Int.MAX_VALUE

    override fun saveState(): Parcelable? {
        return null
    }
}
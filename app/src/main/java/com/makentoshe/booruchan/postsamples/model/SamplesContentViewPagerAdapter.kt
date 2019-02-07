package com.makentoshe.booruchan.postsamples.model

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.postsample.PostSampleScreen
import com.makentoshe.repository.PostsRepository

class SamplesContentViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val postsRepository: PostsRepository
): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PostSampleScreen(position, postsRepository).fragment
    }

    override fun getCount() = Int.MAX_VALUE

    override fun saveState(): Parcelable? {
        return null
    }
}
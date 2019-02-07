package com.makentoshe.booruchan.postsamples.model

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruchan.postsamples.PostSamplesContentScreen
import com.makentoshe.repository.PostsRepository

class SamplesVerticalViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val position: Int,
    private val postsRepository: PostsRepository
) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> PostSamplesContentScreen(this.position, postsRepository).fragment
            else -> throw IllegalArgumentException(position.toString())
        }
    }

    override fun getCount() = 2

    override fun saveState(): Parcelable? {
        return null
    }

}
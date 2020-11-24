package com.makentoshe.booruchan.application.android.screen.samples.model

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.screen.samples.SampleCloseFragment
import com.makentoshe.booruchan.application.android.screen.samples.SampleContentFragment
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post

class SampleFragmentStateAdapter(
    fragment: CoreFragment,
    private val post: Post,
    private val booruContextClass: Class<BooruContext>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        0 -> SampleCloseFragment()
        1 -> SampleContentFragment.build(booruContextClass, post)
        else -> throw IllegalArgumentException()
    }
}


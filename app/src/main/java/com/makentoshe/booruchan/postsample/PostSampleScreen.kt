package com.makentoshe.booruchan.postsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.postsamples.ArgumentsHolder
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository

class PostSampleScreen(
    private val position: Int,
    private val postsRepository: PostsRepository,
    private val samplesRepository: SampleImageRepository
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostSampleFragment().apply {
            //put important arguments
            arguments = Bundle().apply {
                putInt(Int::class.java.simpleName, position)
            }
            //create arguments for holder
            val holderArguments = Bundle().apply {
                putAll(arguments)
                putSerializable(PostsRepository::class.java.simpleName, postsRepository)
                putSerializable(SampleImageRepository::class.java.simpleName, samplesRepository)
            }
            //put arguments to holder
            ArgumentsHolder[this::class.java.simpleName.plus(position)] = holderArguments
        }
}
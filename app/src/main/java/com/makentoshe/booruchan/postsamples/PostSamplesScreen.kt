package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository

class PostSamplesScreen(
    private val position: Int,
    private val postsRepository: PostsRepository,
    private val sampleImageRepository: SampleImageRepository
): FragmentScreen() {
    override val fragment: Fragment
        get() = PostSamplesFragment().apply {
            //put important arguments
            arguments = Bundle().apply {
                putInt(Int::class.java.simpleName, position)
            }
            //create arguments for holder
            val holderArguments = Bundle().apply {
                putAll(arguments)
                putSerializable(PostsRepository::class.java.simpleName, postsRepository)
                putSerializable(SampleImageRepository::class.java.simpleName, sampleImageRepository)
            }
            //put arguments to holder
            ArgumentsHolder[this::class.java.simpleName.plus(position)] = holderArguments
        }
}
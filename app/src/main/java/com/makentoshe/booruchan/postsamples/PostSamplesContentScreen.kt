package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.repository.PostsRepository
import java.text.FieldPosition

class PostSamplesContentScreen(
    private val position: Int,
    private val postsRepository: PostsRepository
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostSamplesContentFragment().apply {
            //put important arguments
            arguments = Bundle().apply {
                putInt(Int::class.java.simpleName, position)
            }
            //create arguments for holder
            val holderArguments = Bundle().apply {
                putAll(arguments)
                putSerializable(PostsRepository::class.java.simpleName, postsRepository)
            }
            //put arguments to holder
            ArgumentsHolder[this::class.java.simpleName.plus(position)] = holderArguments
        }
}
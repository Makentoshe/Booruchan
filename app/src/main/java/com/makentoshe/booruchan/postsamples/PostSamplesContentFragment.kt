package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.postsamples.view.PostSamplesContentUi
import com.makentoshe.repository.PostsRepository
import org.jetbrains.anko.AnkoContext

class PostSamplesContentFragment : com.makentoshe.booruchan.Fragment<PostSamplesContentViewModel>() {

    override fun buildViewModel(arguments: Bundle): PostSamplesContentViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val holderArguments = ArgumentsHolder[this::class.java.simpleName.plus(position)]

        val postsRepository = holderArguments!![PostsRepository::class.java.simpleName] as PostsRepository

        val factory = PostSamplesContentViewModel.Factory(position, postsRepository)
        return ViewModelProviders.of(this, factory)[PostSamplesContentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSamplesContentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }
}


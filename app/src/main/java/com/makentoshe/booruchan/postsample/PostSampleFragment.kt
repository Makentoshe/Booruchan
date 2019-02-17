package com.makentoshe.booruchan.postsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.postsample.view.PostSampleUi
import com.makentoshe.booruchan.postsamples.ArgumentsHolder
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import org.jetbrains.anko.AnkoContext

class PostSampleFragment : Fragment<PostSampleViewModel>() {

    override fun buildViewModel(arguments: Bundle): PostSampleViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)

        val holderArguments = ArgumentsHolder[this::class.java.simpleName.plus(position)]

        val postsRepository = holderArguments!![PostsRepository::class.java.simpleName] as PostsRepository
        val samplesRepository = holderArguments[SampleImageRepository::class.java.simpleName] as SampleImageRepository
        val factory = PostSampleViewModel.Factory(position, postsRepository, samplesRepository)
        return ViewModelProviders.of(this, factory)[PostSampleViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSampleUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"

        fun create(position: Int, booru: Booru): androidx.fragment.app.Fragment {
            return PostSampleFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                    putSerializable(BOORU, booru)
                }
            }
        }
    }
}
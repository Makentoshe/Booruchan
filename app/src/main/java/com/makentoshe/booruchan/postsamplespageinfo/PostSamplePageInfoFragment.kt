package com.makentoshe.booruchan.postsamplespageinfo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.PostSamplePageInfoFragmentViewModelFactory
import com.makentoshe.booruchan.PostsRepository
import org.jetbrains.anko.*


class PostSamplePageInfoFragment : Fragment() {

    private lateinit var viewModel: PostSamplePageInfoFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = buildViewModel(arguments!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSamplePageInfoFragmentUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    private fun buildViewModel(arguments: Bundle): PostSamplePageInfoFragmentViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository

        val factory = PostSamplePageInfoFragmentViewModelFactory(position, postsRepository)
        return ViewModelProviders.of(this, factory)[PostSamplePageInfoFragmentViewModel::class.java]
    }
}
package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.booruchan.postsamples.view.PostSampleFragmentUi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoContext

class PostSampleFragment : Fragment() {

    private lateinit var viewModel: PostsSampleFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = buildViewModel(arguments!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSampleFragmentUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    private fun buildViewModel(arguments: Bundle) : PostsSampleFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        val startPosition = arguments.getInt(Int::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository

        val factory = ViewModelFactory(booru = booru, position = startPosition, postsRepository = postsRepository)
        return ViewModelProviders.of(this, factory)[PostsSampleFragmentViewModel::class.java]
    }

}


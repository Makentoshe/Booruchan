package com.makentoshe.booruchan.postpreviewspage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.PostPageFragmentViewModelFactory
import com.makentoshe.booruchan.PostsRepository
import com.makentoshe.booruchan.PreviewImageRepository
import com.makentoshe.booruchan.ViewModelFragment
import org.jetbrains.anko.AnkoContext

class PostPageFragment : ViewModelFragment<PostPageFragmentViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostPageFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    override fun buildViewModel(arguments: Bundle): PostPageFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        val position = arguments.getInt(PostPageFragment::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository
        val previewsRepository = arguments.getSerializable(PreviewImageRepository::class.java.simpleName) as PreviewImageRepository

        val factory = PostPageFragmentViewModelFactory(booru, position, postsRepository, previewsRepository)
        return ViewModelProviders.of(this, factory)[PostPageFragmentViewModel::class.java]
    }

}
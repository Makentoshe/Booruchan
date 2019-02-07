package com.makentoshe.booruchan.postpreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.postpreview.view.PostPageFragmentUi
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.PreviewImageRepository
import org.jetbrains.anko.AnkoContext

class PostPageFragment : Fragment<PostPageFragmentViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return PostPageFragmentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun buildViewModel(arguments: Bundle): PostPageFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        val position = arguments.getInt(PostPageFragment::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository
        val previewsRepository =
            arguments.getSerializable(PreviewImageRepository::class.java.simpleName) as PreviewImageRepository

        val factory = PostPageFragmentViewModel.Factory(booru, position, postsRepository, previewsRepository)
        return ViewModelProviders.of(this, factory)[PostPageFragmentViewModel::class.java]
    }

}
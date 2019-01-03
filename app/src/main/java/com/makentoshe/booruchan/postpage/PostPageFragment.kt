package com.makentoshe.booruchan.postpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.ViewModelFactory
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.AnkoContext

class PostPageFragment : Fragment() {

//    private lateinit var viewModel: PostPageFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = getViewModel()
//        viewModel.update()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(context).apply {
            val num = arguments!!.getInt(PostPageFragment::class.java.simpleName).toString()
            val tags = arguments!!.getSerializable(Set::class.java.simpleName) as Set<Tag>
            val sas = "$num\n$tags"
            text = sas
        }
//        return PostPageFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

//    private fun getViewModel(): PostPageFragmentViewModel {
//        val booru = arguments!![Booru::class.java.simpleName] as Booru
//        val position = arguments!!.getInt(PostPageFragment::class.java.simpleName)
//        val parentViewModel = getParentFragmentViewModel()
//        val factory = ViewModelFactory(
//            booru = booru,
//            position = position,
//            postsRepository = parentViewModel.postsRepository,
//            previewRepository = parentViewModel.previewRepository)
//        return ViewModelProviders.of(this, factory)[PostPageFragmentViewModel::class.java]
//    }
//
//    private fun getParentFragmentViewModel(): PostsFragmentViewModel {
//        val booru = arguments!![Booru::class.java.simpleName] as Booru
//        val fragment = parentFragment ?: return PostsFragmentViewModel(booru, 12)
//        return ViewModelProviders.of(fragment)[PostsFragmentViewModel::class.java]
//    }

}
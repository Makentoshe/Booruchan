package com.makentoshe.booruchan.application.android.screen.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsFragmentViewModel
import toothpick.ktp.delegate.inject

class PostsFragment : Fragment() {

    companion object {
        fun build(booruContextTitle: String): PostsFragment {
            val fragment = PostsFragment()
            fragment.arguments.booruContextTitle = booruContextTitle
            return fragment
        }
    }

    private val viewModel by inject<PostsFragmentViewModel>()

    val arguments = Arguments(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply { text = "Posts screen not implemented\n${arguments.booruContextTitle}\n$viewModel" }
    }

    class Arguments(private val postsFragment: PostsFragment) {

        init {
            val fragment = postsFragment as Fragment
            if (fragment.arguments == null) {
                fragment.arguments = Bundle()
            }
        }

        private val fragmentArguments: Bundle
            get() = postsFragment.requireArguments()

        var booruContextTitle: String
            get() = fragmentArguments.getString(TITLE)!!
            set(value) = fragmentArguments.putString(TITLE, value)

        companion object {
            private const val TITLE = "BooruContext#title"
        }
    }

}
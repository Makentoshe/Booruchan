package com.makentoshe.booruchan.application.android.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.search.model.PostsSearchTagsAutocompleteAdapter
import com.makentoshe.booruchan.application.android.screen.search.view.DelayMaterialAutocompleteTextView
import kotlinx.android.synthetic.main.fragment_search_posts.*

/**
 * Should be nested fragment for the PostsFragment because this fragment
 * can return search query using [getParentFragment]. If there is no
 * result on [getParentFragment] the nothing will be happen.
 */
class PostsSearchFragment : Fragment() {

    companion object {
        fun build(booruContextTitle: String): PostsSearchFragment {
            val fragment = PostsSearchFragment()
            fragment.arguments.booruContextTitle = booruContextTitle
            return fragment
        }
    }

    val arguments = Arguments(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val autocompleteTextView = (fragment_search_posts_input.editText as DelayMaterialAutocompleteTextView)
        autocompleteTextView.progressBar = fragment_search_posts_progress
        autocompleteTextView.setAdapter(PostsSearchTagsAutocompleteAdapter(requireContext()))
    }

    class Arguments(fragment: PostsSearchFragment) : FragmentArguments<PostsSearchFragment>(fragment) {

        var booruContextTitle: String
            get() = fragmentArguments.getString(TITLE)!!
            set(value) = fragmentArguments.putString(TITLE, value)

        companion object {
            private const val TITLE = "BooruContext#title"
        }
    }
}
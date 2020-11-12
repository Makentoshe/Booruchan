package com.makentoshe.booruchan.application.android.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.search.model.PostsSearchTagsAutocompleteAdapter
import com.makentoshe.booruchan.application.android.screen.search.view.DelayMaterialAutocompleteTextView
import kotlinx.android.synthetic.main.fragment_search_posts.*

/**
 * Should be nested fragment for the PostsFragment because this fragment
 * can return search query using [getParentFragment]. If there is no
 * result on [getParentFragment] the nothing will be happen.
 */
class PostsSearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val autocompleteTextView = (fragment_search_posts_input.editText as DelayMaterialAutocompleteTextView)
        autocompleteTextView.progressBar = fragment_search_posts_progress
        autocompleteTextView.setAdapter(PostsSearchTagsAutocompleteAdapter(requireContext()))
    }

}
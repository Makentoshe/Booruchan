package com.makentoshe.booruchan.application.android.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.search.view.DelayMaterialAutocompleteTextView
import com.makentoshe.booruchan.application.android.screen.search.viewmodel.PostsSearchViewModel
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type
import kotlinx.android.synthetic.main.fragment_search_posts.*
import toothpick.ktp.delegate.inject

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
    private val viewModel by inject<PostsSearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreatedAutocomplete(view, savedInstanceState)
    }

    private fun onViewCreatedAutocomplete(view: View, savedInstanceState: Bundle?) {
        val autocompleteTextView = (fragment_search_posts_input.editText as DelayMaterialAutocompleteTextView)
        autocompleteTextView.progressBar = fragment_search_posts_progress
        autocompleteTextView.setAdapter(viewModel.getTagsAutocompleteAdapter(requireContext()))
        autocompleteTextView.setOnItemClickListener { _, _, position, _ ->
            onAutocompleteTipClick(autocompleteTextView, position)
        }
    }

    private fun onAutocompleteTipClick(autocompleteTextView: DelayMaterialAutocompleteTextView, position: Int) {
        val tag = autocompleteTextView.adapter.getItem(position) as Tag
        autocompleteTextView.setText("")
        when (tag.type) {
            Type.ARTIST -> onArtistTagDisplay(tag)
            Type.GENERAL -> onGeneralTagDisplay(tag)
        }
        // TODO store and display chip under the type category
    }

    private fun onCustomTagDisplay(tag: Tag, group: Group, chipGroup: ChipGroup) {
        group.visibility = View.VISIBLE
        val chip = createChip(tag, chipGroup)
        chipGroup.addView(chip)
        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
            if (chipGroup.childCount == 0) {
                group.visibility = View.GONE
            }
        }
    }

    private fun onArtistTagDisplay(tag: Tag) =
        onCustomTagDisplay(tag, fragment_search_posts_tags_artist, fragment_search_posts_tags_artist_chips)

    private fun onGeneralTagDisplay(tag: Tag) =
        onCustomTagDisplay(tag, fragment_search_posts_tags_general, fragment_search_posts_tags_general_chips)

    private fun createChip(tag: Tag, parent: ViewGroup): Chip {
        val chip = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_search_posts_chip, parent, false)
        chip as Chip
        chip.text = tag.text
        return chip
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
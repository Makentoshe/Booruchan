package com.makentoshe.booruchan.application.android.screen.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.posts.SEARCH_REQUEST_CODE
import com.makentoshe.booruchan.application.android.screen.posts.SEARCH_REQUEST_EXTRA
import com.makentoshe.booruchan.application.android.screen.search.model.CompositeSearchTagsContainer
import com.makentoshe.booruchan.application.android.screen.search.view.DelayMaterialAutocompleteTextView
import com.makentoshe.booruchan.application.android.screen.search.viewmodel.PostsSearchViewModel
import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type
import com.makentoshe.booruchan.core.text
import kotlinx.android.synthetic.main.fragment_search_posts.*
import kotlinx.android.synthetic.main.layout_rating.*
import kotlinx.android.synthetic.main.layout_tags.*
import toothpick.ktp.delegate.inject

/**
 * Should be nested fragment for the PostsFragment because this fragment
 * can return search query using [getParentFragment]. If there is no
 * result on [getParentFragment] the nothing will be happen.
 */
class PostsSearchFragment : CoreFragment() {

    companion object {
        fun build(booruContextTitle: String): PostsSearchFragment {
            val fragment = PostsSearchFragment()
            fragment.arguments.booruContextTitle = booruContextTitle
            return fragment
        }
    }

    val arguments = Arguments(this)

    private val viewModel by inject<PostsSearchViewModel>()

    private val tagsContainer by inject<CompositeSearchTagsContainer>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreatedAutocomplete(view, savedInstanceState)
        onViewCreatedTextInput(view, savedInstanceState)
        onViewCreatedApplyButton()
        onViewCreatedRatingToggle()
    }

    private fun onViewCreatedRatingToggle() {
        fragment_search_posts_rating_toggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
                R.id.fragment_search_posts_rating_toggle_safe -> onSafeRatingClicked(isChecked)
                R.id.fragment_search_posts_rating_toggle_questionable -> onQuestionableRatingClicked(isChecked)
                R.id.fragment_search_posts_rating_toggle_explicit -> onExplicitRatingClicked(isChecked)
            }
            updateApplyButtonAccessibility()
        }
    }

    private fun onSafeRatingClicked(isChecked: Boolean) {
        val tag = text("rating:safe")
        if (isChecked) tagsContainer.addTag(tag) else tagsContainer.remove(tag)
    }

    private fun onQuestionableRatingClicked(isChecked: Boolean) {
        val tag = text("rating:questionable")
        if (isChecked) tagsContainer.addTag(tag) else tagsContainer.remove(tag)
    }

    private fun onExplicitRatingClicked(isChecked: Boolean) {
        val tag = text("rating:explicit")
        if (isChecked) tagsContainer.addTag(tag) else tagsContainer.remove(tag)
    }

    private fun onViewCreatedApplyButton() {
        fragment_search_posts_apply.setOnClickListener {
            val appliedTags = tagsContainer.applyTags()
            val intent = Intent().putExtra(SEARCH_REQUEST_EXTRA, appliedTags.toTypedArray())
            parentFragment?.onActivityResult(SEARCH_REQUEST_CODE, Activity.RESULT_OK, intent)
            updateApplyButtonAccessibility()
        }
    }

    private fun onViewCreatedAutocomplete(view: View, savedInstanceState: Bundle?) {
        val autocompleteTextView = (fragment_search_posts_input.editText as DelayMaterialAutocompleteTextView)
        autocompleteTextView.progressBar = fragment_search_posts_progress
        autocompleteTextView.setAdapter(viewModel.getTagsAutocompleteAdapter(requireContext()))
        autocompleteTextView.setOnItemClickListener { _, _, position, _ ->
            onAutocompleteTipClick(autocompleteTextView, position)
        }
    }

    private fun onViewCreatedTextInput(view: View, savedInstanceState: Bundle?) {
        fragment_search_posts_input.editText?.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> onTextInputImeActionDone(v)
                else -> false
            }
        }
    }

    private fun onTextInputImeActionDone(textView: TextView): Boolean {
        if (processRatingTag(text(textView.text.toString()))) {
            textView.text = ""
            return true
        }

        onGeneralTagDisplay(text(textView.text.toString()))
        textView.text = ""
        fragment_search_posts_progress.visibility = View.GONE
        return true
    }

    /** Return true if tag was processed */
    private fun processRatingTag(tag: Text): Boolean {
        val toggleButton = when (tag.text) {
            "rating:safe" -> fragment_search_posts_rating_toggle_safe
            "rating:questionable" -> fragment_search_posts_rating_toggle_questionable
            "rating:explicit" -> fragment_search_posts_rating_toggle_explicit
            else -> return false
        }
        toggleButton.performClick()
        return true
    }

    private fun onAutocompleteTipClick(autocompleteTextView: DelayMaterialAutocompleteTextView, position: Int) {
        val tag = autocompleteTextView.adapter.getItem(position) as Tag
        autocompleteTextView.setText("")

        if (processRatingTag(tag)) return

        when (tag.type) {
            Type.ARTIST -> onArtistTagDisplay(tag)
            Type.CHARACTER -> onCharacterTagDisplay(tag)
            Type.METADATA -> onMetadataTagDisplay(tag)
            Type.COPYRIGHT -> onCopyrightTagDisplay(tag)
            Type.GENERAL -> onGeneralTagDisplay(tag)
        }
    }

    private fun onCustomTagDisplay(tag: Text, group: View, chipGroup: ChipGroup) {
        // Add tag to container and check - should we display chip or not
        val shouldCreateChip = if (tag is Tag) tagsContainer.addTag(tag) else tagsContainer.addTag(tag)
        if (!shouldCreateChip) return

        // a new tag was added to the search - so it is a time to access applying
        updateApplyButtonAccessibility()

        group.visibility = View.VISIBLE
        val chip = createChip(tag, chipGroup)
        chipGroup.addView(chip)
        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
            if (chipGroup.childCount == 0) {
                group.visibility = View.GONE
            }
            // remove tag as Tag instance or Text instance
            if (tag is Tag) tagsContainer.remove(tag) else tagsContainer.remove(tag)

            // search was changed - so it is a time to access applying
            updateApplyButtonAccessibility()
        }
    }

    private fun onArtistTagDisplay(tag: Text) =
        onCustomTagDisplay(tag, fragment_search_posts_tags_artist, fragment_search_posts_tags_artist_chips)

    private fun onCharacterTagDisplay(tag: Text) =
        onCustomTagDisplay(tag, fragment_search_posts_tags_character, fragment_search_posts_tags_character_chips)

    private fun onMetadataTagDisplay(tag: Text) =
        onCustomTagDisplay(tag, fragment_search_posts_tags_metadata, fragment_search_posts_tags_metadata_chips)

    private fun onCopyrightTagDisplay(tag: Text) =
        onCustomTagDisplay(tag, fragment_search_posts_tags_copyright, fragment_search_posts_tags_copyright_chips)

    private fun onGeneralTagDisplay(tag: Text) =
        onCustomTagDisplay(tag, fragment_search_posts_tags_general, fragment_search_posts_tags_general_chips)

    private fun createChip(tag: Text, parent: ViewGroup): Chip {
        val chip = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_search_posts_chip, parent, false)
        chip as Chip
        chip.text = tag.text
        return chip
    }

    private fun updateApplyButtonAccessibility() {
        fragment_search_posts_apply.isEnabled = tagsContainer.tagsActions.isNotEmpty()
    }

    class Arguments(fragment: PostsSearchFragment) : FragmentArguments(fragment) {

        var booruContextTitle: String
            get() = fragmentArguments.getString(TITLE)!!
            set(value) = fragmentArguments.putString(TITLE, value)

        companion object {
            private const val TITLE = "BooruContext#title"
        }
    }
}


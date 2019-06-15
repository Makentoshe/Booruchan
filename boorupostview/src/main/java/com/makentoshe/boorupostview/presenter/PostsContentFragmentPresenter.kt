package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.AutoCompleteTextView
import com.google.android.material.chip.ChipGroup
import com.makentoshe.boorulibrary.entitiy.Tag
import java.io.Serializable

/**
 * Presenter component for search layout
 */
interface PostsContentFragmentPresenter : Serializable {

    /** Bind a [View] as a search button. Should start search on click */
    fun bindSearchButton(view: View)

    /**
     * Bind an [AutoCompleteTextView] as a search edit text.
     * Should add a tag to the [ChipGroup] on space symbol input.
     * Should autocomplete tags by title and add a tag to the [ChipGroup] on dropdown list element clicked.
     * Should add a tag to the [ChipGroup] and start a search on the ime action.
     */
    fun bindEditText(view: AutoCompleteTextView)

    /**
     * Bind a [ChipGroup]. Contains a Chips as a Tags.
     */
    fun bindChipGroup(view: ChipGroup)
}
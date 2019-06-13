package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.AutoCompleteTextView
import com.google.android.material.chip.ChipGroup
import com.makentoshe.boorulibrary.entitiy.Tag
import java.io.Serializable

interface PostsContentFragmentPresenter : Serializable {

    /**
     * Contains a set of the selected tags.
     */
    val tags: Set<Tag>

    fun bindSearchButton(view: View)

    fun bindEditText(view: AutoCompleteTextView)

    fun bindChipGroup(view: ChipGroup)
}
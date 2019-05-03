package com.makentoshe.booruchan.screen.sampleinfo.controller

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.booru.BooruScreen
import com.makentoshe.booruchan.view.addTagToChipGroup
import org.jetbrains.anko.find
import org.koin.core.KoinComponent
import org.koin.core.inject

class SampleInfoTagsViewController(
    private val root: View,
    private val booru: Booru,
    private val post: Post
) : KoinComponent {

    private val router by inject<Router>()

    private val chipGroup by lazy { root.find<ChipGroup>(R.id.sampleinfo_tags_chipgroup) }

    //contains selected tags for new search
    private val tags = HashSet<Tag>()

    fun bind(fragment: Fragment) {
        post.tags.filter { it.title.isNotBlank() }.forEach { chipGroup.createChip(fragment, it) }

        val searchIcon = getSearchIcon(fragment)
        searchIcon.setOnClickListener {
            router.backTo(BooruScreen(booru, tags))
            router.replaceScreen(BooruScreen(booru, tags))
        }
    }

    private fun ChipGroup.createChip(fragment: Fragment, tag: Tag) = addTagToChipGroup(tag).apply {
        val searchIcon = getSearchIcon(fragment)
        isClickable = true

        setOnCloseIconClickListener {
            isCloseIconVisible = false
            tags.remove(tag)
            if (tags.isEmpty()) searchIcon.visibility = View.GONE
        }

        setOnClickListener {
            isCloseIconVisible = true
            tags.add(tag)
            searchIcon.visibility = View.VISIBLE
        }
    }

    private fun getSearchIcon(fragment: Fragment): View {
        return fragment.requireActivity().find(R.id.sampleinfo_toolbar_tags_search)
    }
}
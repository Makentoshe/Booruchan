package com.makentoshe.booruchan.screen.sampleinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.addTagToChipGroup
import com.makentoshe.booruchan.screen.arguments
import org.jetbrains.anko.*

class SampleInfoTagsFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoTagsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val chipGroup = view.find<ChipGroup>(R.id.sampleinfo_tags_chipgroup)
        post.tags.forEach { chipGroup.createChip(it) }
    }

    private fun ChipGroup.createChip(tag: Tag) {
        addTagToChipGroup(tag)
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SampleInfoTagsFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}
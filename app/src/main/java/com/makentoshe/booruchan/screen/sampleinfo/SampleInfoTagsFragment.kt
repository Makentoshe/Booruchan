package com.makentoshe.booruchan.screen.sampleinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.router
import com.makentoshe.booruchan.screen.booru.BooruScreen
import com.makentoshe.booruchan.screen.sampleinfo.view.SampleInfoTagsUi
import com.makentoshe.booruchan.screen.start.StartScreen
import com.makentoshe.booruchan.view.addTagToChipGroup
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SampleInfoTagsFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val tags: MutableSet<Tag> by lazy { HashSet<Tag>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoTagsUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val chipGroup = view.find<ChipGroup>(R.id.sampleinfo_tags_chipgroup)
        post.tags.filter { it.title.isNotBlank() }.forEach { chipGroup.createChip(it) }

        val searchIcon = requireActivity().find<View>(R.id.sampleinfo_toolbar_tags_search)
        searchIcon.setOnClickListener {
            router.backTo(StartScreen())
            router.navigateTo(BooruScreen(booru, tags))
        }
    }

    private fun ChipGroup.createChip(tag: Tag) {
        addTagToChipGroup(tag).apply {
            isClickable = true

            setOnCloseIconClickListener {
                isCloseIconVisible = false
                tags.remove(tag)
                val searchIcon = requireActivity().find<View>(R.id.sampleinfo_toolbar_tags_search)
                if (tags.isEmpty()) searchIcon.visibility = View.GONE
            }

            setOnClickListener {
                isCloseIconVisible = true
                tags.add(tag)
                val searchIcon = requireActivity().find<View>(R.id.sampleinfo_toolbar_tags_search)
                searchIcon.visibility = View.VISIBLE
            }
        }
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
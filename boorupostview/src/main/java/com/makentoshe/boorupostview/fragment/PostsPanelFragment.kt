package com.makentoshe.boorupostview.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.view.PostsPanelFragmentUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import java.io.Serializable

class PostsPanelFragment : Fragment(), PostsContainerFragment {

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsPanelFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //any display variations
        val fragment = PostsGridScrollFragment.build(booru, tags)

        val id = com.makentoshe.boorupostview.R.id.panelview_content
        childFragmentManager.beginTransaction().add(id, fragment).commit()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = PostsPanelFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}

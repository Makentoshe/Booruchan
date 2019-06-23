package com.makentoshe.boorupostview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.view.PostsPanelFragmentUi
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Fragment for the panel view. It contains a posts viewer.
 */
class PostsPanelFragment : Fragment(), PostsContainerFragment {

    /** Booru API instance */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    /** Current set of the tags to search */
    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsPanelFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = com.makentoshe.boorupostview.R.id.panelview_content
        //check the fragment attached to the container
        attachFragment(id) {
            //attach fragment
            //any display variations here
            //mb using when?
            PostsViewPagerFragment.build(booru, tags)
        }
    }

    /** If fragment does not attached to the [container] - do it */
    private fun attachFragment(container: Int, factory: () -> Fragment) {
        val f = childFragmentManager.findFragmentById(container)
        if (f != null) return else childFragmentManager.beginTransaction().add(container, factory()).commit()
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

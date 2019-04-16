package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.model.SampleHorizontalViewPagerAdapter
import com.makentoshe.booruchan.screen.samples.view.SampleUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import java.io.Serializable

class SampleFragment : Fragment() {

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewpager = view.find<ViewPager>(R.id.samples_container_viewpager)
        //adapter for horizontal scrolling
        viewpager.adapter = SampleHorizontalViewPagerAdapter(childFragmentManager, booru, tags)
        //set adapter to the position on which the click event was invoked
        viewpager.currentItem = position
        //simple fix
        fixFragmentManagerMemoryLeak(childFragmentManager)
    }

    /*
    * Method fixes a strange memory leak. The fragments in fragment manager is not
    * releases after onDestroy and onDetach events. They still alive after rotation event
    * and only parent fragment removing fix the problem. This method removes fragments
    * manually from fragment holder using fragment manager. */
    private fun fixFragmentManagerMemoryLeak(fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        fragmentManager.fragments.forEach {
            transaction.remove(it)
        }
        transaction.commit()
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(
            position: Int,
            booru: Booru,
            tags: Set<Tag>
        ) = SampleFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}
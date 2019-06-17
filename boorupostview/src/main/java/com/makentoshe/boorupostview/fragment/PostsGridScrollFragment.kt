package com.makentoshe.boorupostview.fragment

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.PostsFragmentBroadcastReceiver
import com.makentoshe.boorupostview.model.GridScrollViewPagerAdapter
import com.makentoshe.boorupostview.presenter.PostsGridScrollFragmentRxPresenter
import com.makentoshe.boorupostview.view.PostsGridScrollFragmentUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Performs posts displaying using viewpager and grid view.
 */
class PostsGridScrollFragment : Fragment(), PostsContainerFragment {

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    /** Broadcast receiver for receiving a new search events from another fragment */
    private val broadcastReceiver = PostsFragmentBroadcastReceiver()

    /** Contains a disposable which will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    private lateinit var presenter: PostsGridScrollFragmentRxPresenter

    /** Register receiver */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        PostsFragmentBroadcastReceiver.registerReceiver(requireActivity(), broadcastReceiver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsGridScrollFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // restore tags from the saved state
        val tags: Set<Tag> = if (savedInstanceState == null) tags else extractTagsFromState(savedInstanceState)
        // create adapter builder for the presenter
        val adapterBuilder = GridScrollViewPagerAdapter.Builder(childFragmentManager, booru)
        // create presenter
        presenter = PostsGridScrollFragmentRxPresenter(disposables, adapterBuilder, broadcastReceiver, tags)
        //bind view pager
        val viewpager = view.findViewById<ViewPager>(com.makentoshe.boorupostview.R.id.viewpager)
        presenter.bindViewPager(viewpager)
    }

    /** Extracts a set of the [Tag] from the [Bundle] or return an empty set */
    private fun extractTagsFromState(state: Bundle): Set<Tag> {
        return if (state.containsKey(TAGS)) state.get(TAGS) as Set<Tag> else emptySet()
    }

    /** Save a tags to the state */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(TAGS, presenter.tags as Serializable)
    }

    /** Unregister receiver */
    override fun onDetach() {
        super.onDetach()
        requireActivity().unregisterReceiver(broadcastReceiver)
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = PostsGridScrollFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}

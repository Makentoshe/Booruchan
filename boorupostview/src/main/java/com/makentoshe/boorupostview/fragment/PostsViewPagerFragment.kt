package com.makentoshe.boorupostview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.RepositoryBuilder
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.NewSearchBroadcastReceiver
import com.makentoshe.boorupostview.model.AndroidImageDecoder
import com.makentoshe.boorupostview.model.GridElementControllerHolder
import com.makentoshe.boorupostview.model.PostsViewPagerAdapter
import com.makentoshe.boorupostview.presenter.PostsViewPagerFragmentPresenter
import com.makentoshe.boorupostview.view.PostsViewPagerFragmentUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Performs posts displaying using viewpager and grid view.
 */
class PostsViewPagerFragment : Fragment(), PostsContainerFragment {

    /** Broadcast receiver for receiving a new search events from another fragment */
    private val broadcastReceiver = NewSearchBroadcastReceiver()

    /** Contains a disposable which will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Booru API instance */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    /** Current set of the tags to search */
    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    /** Presenter component uses for a receiving set of the selected but not searched yet tags */
    private lateinit var presenter: PostsViewPagerFragmentPresenter

    /** Register receiver */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        NewSearchBroadcastReceiver.registerReceiver(requireActivity(), broadcastReceiver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsViewPagerFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // restore tags from the saved state
        val tags: Set<Tag> = if (savedInstanceState == null) tags else extractTagsFromState(savedInstanceState)

        val imageDecoder = AndroidImageDecoder()
        val repositoryBuilder = RepositoryBuilder(booru)
        val cacheBuilder = CacheBuilder(requireContext())
        val controllerHolder =
            GridElementControllerHolder.Builder(repositoryBuilder, cacheBuilder, imageDecoder).build(this)
        // create adapter builder for the presenter
        val adapterBuilder = PostsViewPagerAdapter.Builder(childFragmentManager, booru, controllerHolder)
        // create presenter
        presenter = PostsViewPagerFragmentPresenter(disposables, adapterBuilder, broadcastReceiver, tags)
        //bind view pager
        val viewpager = view.findViewById<ViewPager>(com.makentoshe.boorupostview.R.id.viewpager)
        presenter.bindViewPager(viewpager)
        val bottombar = view.findViewById<View>(com.makentoshe.boorupostview.R.id.bottombar)
        presenter.bindBottomBar(bottombar)
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
            val fragment = PostsViewPagerFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}
